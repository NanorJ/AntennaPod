import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.benchmark.BenchmarkState;
import androidx.benchmark.junit4.BenchmarkRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

@RunWith((AndroidJUnit4.class))
public class SampleBenchmark {
    @Rule
    public BenchmarkRule benchmarkRule = new BenchmarkRule();

    @Test
    public void benchSomeWork() {
        final BenchmarkState state = benchmarkRule.getState();
        while (state.keepRunning()){
           //MediaType result = PlaybackService.getCurrentMediaType();
           System.out.println("helllllllllooooooooooooooooooooooo");
        }
    }
}
