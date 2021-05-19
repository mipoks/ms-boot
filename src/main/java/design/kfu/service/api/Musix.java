package design.kfu.service.api;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import design.kfu.service.api.message.ErrorMessage;
import design.kfu.service.api.exception.MusixException;
import design.kfu.service.api.message.LyricsGetMessage;
import design.kfu.service.api.message.TrackGetMessage;
import design.kfu.service.api.model.*;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Musix {

    private final String apiKey;
    public Musix(String apiKey) {
        this.apiKey = apiKey;
    }

    public Lyrics getLyrics(int trackID) throws MusixException {
        Map<String, Object> params = new HashMap<>();

        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.TRACK_ID, String.valueOf(trackID));

        String response = sendRequest(getURLString(
                Methods.TRACK_LYRICS_GET, params));
        try {
            Gson gson = new Gson();
            LyricsGetMessage message = gson.fromJson(response, LyricsGetMessage.class);
            return message.getContainer().getBody().getLyrics();
        } catch (JsonParseException jpe) {
            handleErrorResponse(response);
        }
        return null;
    }

    public Track getMatchingTrack(String q_track, String q_artist)
            throws MusixException {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.QUERY_TRACK, q_track);
        params.put(Constants.QUERY_ARTIST, q_artist);

        Track track = getTrackResponse(Methods.MATCHER_TRACK_GET, params);
        return track;
    }

    private Track getTrackResponse(String methodName, Map<String, Object> params)
            throws MusixException {
        Track track = new Track();
        TrackGetMessage message = null;
        String response = sendRequest(getURLString(
                methodName, params));
        Gson gson = new Gson();
        try {
            message = gson.fromJson(response, TrackGetMessage.class);
        } catch (JsonParseException jpe) {
            handleErrorResponse(response);
        }
        TrackData data = message.getTrackMessage().getBody().getTrack();
        track.setTrack(data);
        return track;
    }

    private void handleErrorResponse(String jsonResponse)
            throws MusixException {
        Gson gson = new Gson();
        log.info(jsonResponse);

        System.out.println(jsonResponse);
        int responseCode = 500;
        try {
            ErrorMessage errMessage = gson.fromJson(jsonResponse, ErrorMessage.class);
            responseCode = errMessage.getMessageContainer().getHeader().getStatusCode();
        } catch (Exception ex) {
        }

        String info;
        switch (responseCode) {
            case 400:
                info = "The request had bad syntax or was inherently impossible to be satisfied";
                break;
            case 402:
                info = "a limit was reached, either you exceeded per hour requests limits or your balance is insufficient.";
                break;
            case 401:
                info = "The request had bad syntax or was inherently impossible to be satisfied";
                break;
            case 403:
                info = "You are not authorized to perform this operation / the api version you�re trying to use has been shut down.";
                break;
            case 404:
                info = "requested resource was not found";
                break;
            case 405:
                info = "requested method was not found";
                break;
            default:
                info = "An error has occured while performing the query. Please contact the administrator.";
                break;
        }
        throw new MusixException(info);
    }

    private static String sendRequest(String requestString)
            throws MusixException {
        StringBuilder buffer = new StringBuilder();
        try {
            String apiUrl = Constants.API_URL + Constants.API_VERSION + Constants.URL_DELIM + requestString;
            URL url = new URL(apiUrl);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                buffer.append(str);
            }
            in.close();
        } catch (IOException e) {
            throw new MusixException(e.getMessage());
        }
        return buffer.toString();
    }

    private static String getURLString(String methodName,
                                      Map<String, Object> params) throws MusixException {
        StringBuilder paramString = new StringBuilder(methodName + "?");
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            try {
                paramString.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue().toString(),
                        "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new MusixException("Problem encoding "
                        + entry.getValue(), e);
            }
            paramString.append("&");
        }
        paramString = paramString.deleteCharAt(paramString.length() - 1);
        return paramString.toString();
    }
}
