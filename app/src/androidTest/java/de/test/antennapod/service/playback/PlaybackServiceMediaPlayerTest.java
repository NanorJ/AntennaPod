package de.test.antennapod.service.playback;

import android.content.Context;

import androidx.test.annotation.UiThreadTest;
import androidx.test.filters.MediumTest;

import de.test.antennapod.EspressoTestUtils;
import junit.framework.AssertionFailedError;
import org.apache.commons.io.IOUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import de.danoeh.antennapod.core.storage.PodDBAdapter;
import de.test.antennapod.util.service.download.HTTPBin;
import org.junit.After;
import org.junit.Before;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class for LocalPSMP
 */
@MediumTest
public class PlaybackServiceMediaPlayerTest {
    private static final String PLAYABLE_DEST_URL = "psmptestfile.mp3";
    private String PLAYABLE_LOCAL_URL = null;
    private static final int LATCH_TIMEOUT_SECONDS = 3;

    private HTTPBin httpServer;
    private String playableFileUrl;
    private volatile AssertionFailedError assertionError;

    @After
    @UiThreadTest
    public void tearDown() throws Exception {
        PodDBAdapter.deleteDatabase();
        httpServer.stop();
    }

    @Before
    @UiThreadTest
    public void setUp() throws Exception {
        assertionError = null;
        EspressoTestUtils.clearPreferences();
        EspressoTestUtils.clearDatabase();

        final Context context = getInstrumentation().getTargetContext();

        httpServer = new HTTPBin();
        httpServer.start();
        playableFileUrl = httpServer.getBaseUrl() + "/files/0";

        File cacheDir = context.getExternalFilesDir("testFiles");
        if (cacheDir == null)
            cacheDir = context.getExternalFilesDir("testFiles");
        File dest = new File(cacheDir, PLAYABLE_DEST_URL);

        assertNotNull(cacheDir);
        assertTrue(cacheDir.canWrite());
        assertTrue(cacheDir.canRead());
        if (!dest.exists()) {
            InputStream i = getInstrumentation().getTargetContext().getAssets().open("3sec.mp3");
            OutputStream o = new FileOutputStream(new File(cacheDir, PLAYABLE_DEST_URL));
            IOUtils.copy(i, o);
            o.flush();
            o.close();
            i.close();
        }
        PLAYABLE_LOCAL_URL = dest.getAbsolutePath();
        assertEquals(0, httpServer.serveFile(dest));
    }
}
