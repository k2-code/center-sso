package com.center.sso.phili.utils;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.Page;

@JsonInclude(Include.NON_NULL)
public class PageResponse<T> implements Serializable {
    private static final long serialVersionUID = -2276949234251897353L;
    private Long total;
    private Integer pageIndex;
    private Integer pageSize;
    private Collection<T> dataList;

    public PageResponse(Page<T> data) {
        this.total = data.getTotalElements();
        this.pageIndex = data.getNumber() + 1;
        this.pageSize = data.getSize();
        this.dataList = data.getContent();
    }

    public PageResponse(Integer total, PageParam param, List<T> dataList) {
        this.total = total.longValue();
        this.pageIndex = param.getPageIndex();
        this.pageSize = param.getPageSize();
        this.dataList = dataList;
    }

    public PageResponse(Long total, List<T> dataList) {
        this.total = total;
        this.dataList = dataList;
    }

    public static PageResponse getEmptyPageResponse(Integer pageSize, Integer pageIndex) {
        PageResponse<Object> objectPageResponse = new PageResponse();
        objectPageResponse.setPageSize(pageSize);
        objectPageResponse.setPageIndex(pageIndex);
        objectPageResponse.setDataList(Collections.emptyList());
        return objectPageResponse;
    }

    public static <T> PageResponseBuilder<T> builder() {
        return new PageResponseBuilder();
    }

    public Long getTotal() {
        return this.total;
    }

    public Integer getPageIndex() {
        return this.pageIndex;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public Collection<T> getDataList() {
        return this.dataList;
    }

    @JsonView({ResultResponse.PageSimpleView.class, ResultResponse.DataSimpleView.class})
    public void setTotal(Long total) {
        this.total = total;
    }

    @JsonView({ResultResponse.PageSimpleView.class, ResultResponse.DataSimpleView.class})
    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    @JsonView({ResultResponse.PageSimpleView.class, ResultResponse.DataSimpleView.class})
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @JsonView({ResultResponse.PageSimpleView.class, ResultResponse.DataSimpleView.class})
    public void setDataList(Collection<T> dataList) {
        this.dataList = dataList;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PageResponse)) {
            return false;
        } else {
            PageResponse<?> other = (PageResponse)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59: {
                    Object this$total = this.getTotal();
                    Object other$total = other.getTotal();
                    if (this$total == null) {
                        if (other$total == null) {
                            break label59;
                        }
                    } else if (this$total.equals(other$total)) {
                        break label59;
                    }

                    return false;
                }

                Object this$pageIndex = this.getPageIndex();
                Object other$pageIndex = other.getPageIndex();
                if (this$pageIndex == null) {
                    if (other$pageIndex != null) {
                        return false;
                    }
                } else if (!this$pageIndex.equals(other$pageIndex)) {
                    return false;
                }

                Object this$pageSize = this.getPageSize();
                Object other$pageSize = other.getPageSize();
                if (this$pageSize == null) {
                    if (other$pageSize != null) {
                        return false;
                    }
                } else if (!this$pageSize.equals(other$pageSize)) {
                    return false;
                }

                Object this$dataList = this.getDataList();
                Object other$dataList = other.getDataList();
                if (this$dataList == null) {
                    if (other$dataList != null) {
                        return false;
                    }
                } else if (!this$dataList.equals(other$dataList)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof PageResponse;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $total = this.getTotal();
        result = result * 59 + ($total == null ? 43 : $total.hashCode());
        Object $pageIndex = this.getPageIndex();
        result = result * 59 + ($pageIndex == null ? 43 : $pageIndex.hashCode());
        Object $pageSize = this.getPageSize();
        result = result * 59 + ($pageSize == null ? 43 : $pageSize.hashCode());
        Object $dataList = this.getDataList();
        result = result * 59 + ($dataList == null ? 43 : $dataList.hashCode());
        return result;
    }

    public String toString() {
        return "PageResponse(total=" + this.getTotal() + ", pageIndex=" + this.getPageIndex() + ", pageSize=" + this.getPageSize() + ", dataList=" + this.getDataList() + ")";
    }

    public PageResponse(Long total, Integer pageIndex, Integer pageSize, Collection<T> dataList) {
        this.total = total;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.dataList = dataList;
    }

    public PageResponse() {
    }

    public static class PageResponseBuilder<T> {
        private Long total;
        private Integer pageIndex;
        private Integer pageSize;
        private Collection<T> dataList;

        PageResponseBuilder() {
        }

        @JsonView({ResultResponse.PageSimpleView.class, ResultResponse.DataSimpleView.class})
        public PageResponseBuilder<T> total(Long total) {
            this.total = total;
            return this;
        }

        @JsonView({ResultResponse.PageSimpleView.class, ResultResponse.DataSimpleView.class})
        public PageResponseBuilder<T> pageIndex(Integer pageIndex) {
            this.pageIndex = pageIndex;
            return this;
        }

        @JsonView({ResultResponse.PageSimpleView.class, ResultResponse.DataSimpleView.class})
        public PageResponseBuilder<T> pageSize(Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        @JsonView({ResultResponse.PageSimpleView.class, ResultResponse.DataSimpleView.class})
        public PageResponseBuilder<T> dataList(Collection<T> dataList) {
            this.dataList = dataList;
            return this;
        }

        public PageResponse<T> build() {
            return new PageResponse(this.total, this.pageIndex, this.pageSize, this.dataList);
        }

        public String toString() {
            return "PageResponse.PageResponseBuilder(total=" + this.total + ", pageIndex=" + this.pageIndex + ", pageSize=" + this.pageSize + ", dataList=" + this.dataList + ")";
        }
    }
}

