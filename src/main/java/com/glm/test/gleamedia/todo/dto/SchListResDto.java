package com.glm.test.gleamedia.todo.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class SchListResDto<T> {
    //검색된 item 갯수
    private long count;

    //페이징 번호,
    private int page;

    //한 페이지당 노출 건수
    private int unit;

    //검색 결과
    private List<T> item;

    private SchListResDto(Page<T> page) {
        this.count = page.getTotalElements();
        this.page = page.getPageable().getPageNumber() + 1;
        this.unit = page.getPageable().getPageSize();
    }

    public static <T> SchListResDto of(Page page, List<T> item) {
        SchListResDto res = new SchListResDto<T>(page);
        res.setItem(item);
        return res;
    }

    public int getTotalPage() {
        return (int) Math.ceil((double) this.count / (double) this.unit);
    }

    public boolean isFirst() {
        return this.page == 1;
    }

    public boolean isLast() {
        return this.page == getTotalPage();
    }
}
