package delta.dkt.activities;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;

import static org.mockito.Mockito.*;

//@RunWith(JUnitPlatform.class)
public class GameViewActivityTest {
    @InjectMocks
    private static GameViewActivity gameViewActivity;
    @Mock
    private AppCompatActivity mockActivity;

    @BeforeEach
    void setUp() {
        gameViewActivity = new GameViewActivity();
    }

    @Test
    public void testIntentSwitch() {
        Intent expectedIntent = new Intent(mockActivity, PropertyListActivity.class);

        gameViewActivity.switchToPropertyActivity();

        verify(mockActivity).startActivity(expectedIntent);
    }
}
