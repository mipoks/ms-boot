package design.kfu.helper.music.implementation.v2;

import design.kfu.entity.music.Song;
import design.kfu.helper.music.MusicGetter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.*;

@Slf4j
@Component
@Primary
public class GetDeezerMusic implements MusicGetter {
    @Override
    public Set<Song> get(int count) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.deezer.com/chart")
                .get()
                .addHeader("x-rapidapi-key", "6d88d9b7edmsh9c0e25d90ba65e3p192a13jsn2003c64a93c7")
                .addHeader("x-rapidapi-host", "deezerdevs-deezer.p.rapidapi.com")
                .build();

        try {
            Response response = client.newCall(request).execute();
            return parseResponse(response, count);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<Song> get(int count, design.kfu.entity.music.Request myRequest) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.deezer.com/search?q=" + myRequest.getSearch())
                .get()
                .addHeader("x-rapidapi-key", "6d88d9b7edmsh9c0e25d90ba65e3p192a13jsn2003c64a93c7")
                .addHeader("x-rapidapi-host", "deezerdevs-deezer.p.rapidapi.com")
                .build();

        try {
            Response response = client.newCall(request).execute();
            return parseResponse(response, count);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Set<Song> parseResponse(Response response, int count) {
        try {
            String json = Objects.requireNonNull(response.body()).string();
            JSONObject o = new JSONObject(json);
            JSONArray tracks = null;
            try {
                tracks = o.getJSONObject("tracks").getJSONArray("data");
            } catch (Exception ex) {
                tracks = o.getJSONArray("data");
            }
            int num = 0;
            Set<Song> songs = new LinkedHashSet<>();
            for (int i = 0; i < tracks.length(); i++) {
                JSONObject obj = new JSONObject(tracks.get(i).toString());
                String artistName = obj.getJSONObject("artist").get("name").toString();
                String title = obj.get("title").toString();
                String link = obj.get("preview").toString();
                Long id = Long.parseLong(obj.get("id").toString());

                Song songTemp = new Song();
                songTemp.setId(id);
                songTemp.setSongName(title + " – " + artistName);
                songTemp.setOriginalUrl(link);
                songTemp.setUrl(link);
                log.info(songTemp.toString());
                songs.add(songTemp);
                num++;
                if (num == count)
                    break;
            }
            return songs;
        } catch (NullPointerException | IOException | JSONException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Set<Song> list) throws IOException {
    }
}
