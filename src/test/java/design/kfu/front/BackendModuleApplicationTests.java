package design.kfu.front;

import design.kfu.config.security.details.UserDetailsImpl;
import design.kfu.entity.dto.PersonForm;
import design.kfu.entity.music.Person;
import design.kfu.entity.music.Song;
import design.kfu.service.SignUpService;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import design.kfu.service.ProfileService;
import design.kfu.service.music.MusicService;
import design.kfu.service.music.PersonMusicService;
import design.kfu.service.music.PersonService;
import design.kfu.service.music.SongTextService;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.Set;

@AutoConfigureEmbeddedDatabase(beanName = "dataSource")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BackendModuleApplicationTests {

	@Autowired
	private MusicService musicService;

	@Autowired
	private PersonMusicService personMusicService;

	@Autowired
	private PersonService personService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private SongTextService songTextService;

	@Autowired
	private SignUpService signUpService;

	@Autowired
	@Qualifier("customUserDetailsService")
	private UserDetailsService userDetailsService;

	@Test
	void contextLoads() {
	}

	@Test
	void searchMusicNotNullTest() throws IOException {
		Set<Song> songs = musicService.search("Feduk", null);
		assertNotEquals(songs, null);
	}

	@Test
	void searchMusicTest() throws IOException {
		Set<Song> songs = musicService.search("Feduk", null);
		assertTrue(songs.size() > 0);
	}

	@Test
	void getSongTextTestNotNull() {
		assertNotEquals(songTextService.getText("Хлопья летят наверх", "Feduk"), null);
	}

	@Test
	void getSongTextTest() {
		assertEquals(songTextService.getText("Хлопья летят наверх", "Feduk").getName(), "Хлопья летят наверх");
	}

	@Test
	@Order(1)
	void signUpServiceTest() {
		PersonForm personForm = PersonForm.builder().email("wow@test.ru").name("Tester").password("Test123!").songCount(5).build();
		long ans = signUpService.signUp(personForm);
		assertTrue(ans >= 0 && ans <= 1);
	}

	@Test
	@Order(2)
	void userDetailsServiceNotNullTest() {
		UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername("wow@test.ru");
		Person person = userDetails.getUser();
		assertNotNull(person);
	}

	@Test
	@Order(3)
	void userDetailsServiceTest() {
		UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername("wow@test.ru");
		Person person = userDetails.getUser();
		assertEquals(person.getEmail(), "wow@test.ru");
	}

	@Test
	@Order(4)
	void profileServiceTest() {
		UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername("wow@test.ru");
		Person person = userDetails.getUser();

		PersonForm personForm = PersonForm.builder()
				.password(person.getPassword())
				.name(person.getName())
				.email(person.getEmail())
				.songCount(11)
				.build();
		long result = profileService.update(personForm, person);
		assertEquals(result, 1);
	}
}
