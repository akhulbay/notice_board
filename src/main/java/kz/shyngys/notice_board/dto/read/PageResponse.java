package kz.shyngys.notice_board.dto.read;


import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageResponse<T> {

    public MetaData metaData;
    public List<T> data;

    @Getter
    public static class MetaData {
        int page;
        int size;
        boolean hasNext;

        public static MetaData of(int page, int size, boolean hasNext) {
            MetaData meta = new MetaData();

            meta.page = page;
            meta.size = size;
            meta.hasNext = hasNext;

            return meta;
        }
    }

    public static <T> PageResponse<T> of(Page<T> page) {
        PageResponse<T> response = new PageResponse<>();

        response.data = page.getContent();
        response.metaData = MetaData.of(page.getNumber(), page.getSize(), page.hasNext());

        return response;
    }

    public static <T> PageResponse<T> of(List<T> data, int page, int size, boolean hasNext) {
        PageResponse<T> response = new PageResponse<>();

        response.data = data;
        response.metaData = MetaData.of(page, size, hasNext);

        return response;
    }

}
