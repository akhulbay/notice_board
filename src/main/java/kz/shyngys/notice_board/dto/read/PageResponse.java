package kz.shyngys.notice_board.dto.read;


import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public class PageResponse<T> {

    public MetaData metaData;
    public List<T> data;

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

    public static <R, T> PageResponse<T> of(Page<R> page, Function<R, T> dataMapper) {
        PageResponse<T> response = new PageResponse<>();

        response.metaData = MetaData.of(page.getNumber(), page.getSize(), page.hasNext());

        response.data = page.getContent().stream()
                .map(dataMapper)
                .toList();

        return response;
    }

}
