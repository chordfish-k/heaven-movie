import com.chord.lsr.MovieBackApplication;
import com.chord.lsr.properties.RankItemProperties;
import com.chord.lsr.properties.RankProperties;
import com.chord.lsr.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = MovieBackApplication.class)
public class RankTest {
    @Autowired
    private RankProperties rankProperties;
    @Test
    public void testRankProps(){
        List<RankItemProperties> rankList = rankProperties.getRankList();
        System.out.println(rankList);
    }
}
