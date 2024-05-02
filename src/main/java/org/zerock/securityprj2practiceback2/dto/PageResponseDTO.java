package org.zerock.securityprj2practiceback2.dto;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResponseDTO<E> {

    private List<E> dtoList;

    private List<Integer> pageNumList;

    private PageRequestDTO pageRequestDTO;

    private boolean prev, next;

    private int totalCount, prevPage, nextPage, totalPage, current;

    public PageResponseDTO(List<E> dtoList, PageRequestDTO pageRequestDTO, long total) {

        this.dtoList = dtoList;
        this.pageRequestDTO = pageRequestDTO;
        this.totalCount = (int)total;

        // 끝 페이지 end
        int end = (int) (Math.ceil(pageRequestDTO.getPage() / 10.0)) * 10;

        // 시작 페이지
        int start = end - 9;

        // 진짜 마지막 페이지
        int last = (int)(Math.ceil(totalCount / (double)pageRequestDTO.getSize()));

        // 만약 end 진자 마지막 페이지 보다 크면 last 로 바꾸기
        end = end > last ? last : end;

        // 이전 버튼은 시작 값이 1보다 클때만 나오게
        this.prev = start > 1;

        // 다음 버튼
        this.next = totalCount > end * pageRequestDTO.getSize();

        // 페이지 버튼 묶음
        this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

        // 이전 페이지 값
        this.prevPage = prev ? start - 1 : 0;

        // 다음 페이지 값
        this.nextPage = next ? end + 1 : 0;

        this.totalPage = this.pageNumList.size();
        this.current = pageRequestDTO.getPage();



    }

}
