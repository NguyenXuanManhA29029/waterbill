package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Thue} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.ThueResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /thues?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ThueCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tenThue;

    private LongFilter giaTriThue;

    public ThueCriteria() {}

    public ThueCriteria(ThueCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tenThue = other.tenThue == null ? null : other.tenThue.copy();
        this.giaTriThue = other.giaTriThue == null ? null : other.giaTriThue.copy();
    }

    @Override
    public ThueCriteria copy() {
        return new ThueCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTenThue() {
        return tenThue;
    }

    public StringFilter tenThue() {
        if (tenThue == null) {
            tenThue = new StringFilter();
        }
        return tenThue;
    }

    public void setTenThue(StringFilter tenThue) {
        this.tenThue = tenThue;
    }

    public LongFilter getGiaTriThue() {
        return giaTriThue;
    }

    public LongFilter giaTriThue() {
        if (giaTriThue == null) {
            giaTriThue = new LongFilter();
        }
        return giaTriThue;
    }

    public void setGiaTriThue(LongFilter giaTriThue) {
        this.giaTriThue = giaTriThue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ThueCriteria that = (ThueCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(tenThue, that.tenThue) && Objects.equals(giaTriThue, that.giaTriThue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tenThue, giaTriThue);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThueCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (tenThue != null ? "tenThue=" + tenThue + ", " : "") +
            (giaTriThue != null ? "giaTriThue=" + giaTriThue + ", " : "") +
            "}";
    }
}
