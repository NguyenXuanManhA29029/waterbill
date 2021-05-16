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
 * Criteria class for the {@link com.mycompany.myapp.domain.TaiKhoan} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.TaiKhoanResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tai-khoans?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TaiKhoanCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter userName;

    private StringFilter passWord;

    private LongFilter hoGiaDinhId;

    public TaiKhoanCriteria() {}

    public TaiKhoanCriteria(TaiKhoanCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.userName = other.userName == null ? null : other.userName.copy();
        this.passWord = other.passWord == null ? null : other.passWord.copy();
        this.hoGiaDinhId = other.hoGiaDinhId == null ? null : other.hoGiaDinhId.copy();
    }

    @Override
    public TaiKhoanCriteria copy() {
        return new TaiKhoanCriteria(this);
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

    public StringFilter getUserName() {
        return userName;
    }

    public StringFilter userName() {
        if (userName == null) {
            userName = new StringFilter();
        }
        return userName;
    }

    public void setUserName(StringFilter userName) {
        this.userName = userName;
    }

    public StringFilter getPassWord() {
        return passWord;
    }

    public StringFilter passWord() {
        if (passWord == null) {
            passWord = new StringFilter();
        }
        return passWord;
    }

    public void setPassWord(StringFilter passWord) {
        this.passWord = passWord;
    }

    public LongFilter getHoGiaDinhId() {
        return hoGiaDinhId;
    }

    public LongFilter hoGiaDinhId() {
        if (hoGiaDinhId == null) {
            hoGiaDinhId = new LongFilter();
        }
        return hoGiaDinhId;
    }

    public void setHoGiaDinhId(LongFilter hoGiaDinhId) {
        this.hoGiaDinhId = hoGiaDinhId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TaiKhoanCriteria that = (TaiKhoanCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(userName, that.userName) &&
            Objects.equals(passWord, that.passWord) &&
            Objects.equals(hoGiaDinhId, that.hoGiaDinhId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, passWord, hoGiaDinhId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaiKhoanCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (userName != null ? "userName=" + userName + ", " : "") +
            (passWord != null ? "passWord=" + passWord + ", " : "") +
            (hoGiaDinhId != null ? "hoGiaDinhId=" + hoGiaDinhId + ", " : "") +
            "}";
    }
}
