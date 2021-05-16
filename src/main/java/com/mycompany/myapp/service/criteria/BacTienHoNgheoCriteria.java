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
 * Criteria class for the {@link com.mycompany.myapp.domain.BacTienHoNgheo} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.BacTienHoNgheoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /bac-tien-ho-ngheos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BacTienHoNgheoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tenBac;

    private LongFilter giaTriBac;

    public BacTienHoNgheoCriteria() {}

    public BacTienHoNgheoCriteria(BacTienHoNgheoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tenBac = other.tenBac == null ? null : other.tenBac.copy();
        this.giaTriBac = other.giaTriBac == null ? null : other.giaTriBac.copy();
    }

    @Override
    public BacTienHoNgheoCriteria copy() {
        return new BacTienHoNgheoCriteria(this);
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

    public StringFilter getTenBac() {
        return tenBac;
    }

    public StringFilter tenBac() {
        if (tenBac == null) {
            tenBac = new StringFilter();
        }
        return tenBac;
    }

    public void setTenBac(StringFilter tenBac) {
        this.tenBac = tenBac;
    }

    public LongFilter getGiaTriBac() {
        return giaTriBac;
    }

    public LongFilter giaTriBac() {
        if (giaTriBac == null) {
            giaTriBac = new LongFilter();
        }
        return giaTriBac;
    }

    public void setGiaTriBac(LongFilter giaTriBac) {
        this.giaTriBac = giaTriBac;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BacTienHoNgheoCriteria that = (BacTienHoNgheoCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(tenBac, that.tenBac) && Objects.equals(giaTriBac, that.giaTriBac);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tenBac, giaTriBac);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BacTienHoNgheoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (tenBac != null ? "tenBac=" + tenBac + ", " : "") +
            (giaTriBac != null ? "giaTriBac=" + giaTriBac + ", " : "") +
            "}";
    }
}
