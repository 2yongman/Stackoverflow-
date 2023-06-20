package BackEnd.preProject.response;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class CursorResult<T> {
    private List<T> data;
    private Boolean hasNext;

    public CursorResult(List<T> data, Boolean hasNext){
        this.data = data;
        this.hasNext = hasNext;
    }
}
