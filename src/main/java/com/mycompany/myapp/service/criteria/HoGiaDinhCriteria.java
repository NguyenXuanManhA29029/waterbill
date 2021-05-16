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
 * Criteria class for the {@link com.mycompany.myapp.domain.HoGiaDinh} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.HoGiaDinhResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ho-gia-dinhs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class HoGiaDinhCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tenChuHo;

    private StringFilter maHo;

    private StringFilter soCanCuoc;

    private StringFilter loaiHo;

    private StringFilter soHoNgheo;

    private StringFilter email;

    private StringFilter sdt;

    private StringFilter diaChi;

    private LongFilter taikhoanId;

    private LongFilter hoadongdId;

    public HoGiaDinhCriteria() {}

    public HoGiaDinhCriteria(HoGiaDinhCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tenChuHo = other.tenChuHo == null ? null : other.tenChuHo.copy();
        this.maHo = other.maHo == null ? null : other.maHo.copy();
        this.soCanCuoc = other.soCanCuoc == null ? null : other.soCanCuoc.copy();
        this.loaiHo = other.loaiHo == null ? null : other.loaiHo.copy();
        this.soHoNgheo = other.soHoNgheo == null ? null : other.soHoNgheo.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.sdt = other.sdt == null ? null : other.sdt.copy();
        this.diaChi = other.diaChi == null ? null : other.diaChi.copy();
        this.taikhoanId = other.taikhoanId == null ? null : other.taikhoanId.copy();
        this.hoadongdId = other.hoadongdId == null ? null : other.hoadongdId.copy();
    }

    @Override
    public HoGiaDinhCriteria copy() {
        return new HoGiaDinhCriteria(this);
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

    public StringFilter getTenChuHo() {
        return tenChuHo;
    }

    public StringFilter tenChuHo() {
        if (tenChuHo == null) {
            tenChuHo = new StringFilter();
        }
        return tenChuHo;
    }

    public void setTenChuHo(StringFilter tenChuHo) {
        this.tenChuHo = tenChuHo;
    }

    public StringFilter getMaHo() {
        return maHo;
    }

    public StringFilter maHo() {
        if (maHo == null) {
            maHo = new StringFilter();
        }
        return maHo;
    }

    public void setMaHo(StringFilter maHo) {
        this.maHo = maHo;
    }

    public StringFilter getSoCanCuoc() {
        return soCanCuoc;
    }

    public StringFilter soCanCuoc() {
        if (soCanCuoc == null) {
            soCanCuoc = new StringFilter();
        }
        return soCanCuoc;
    }

    public void setSoCanCuoc(StringFilter soCanCuoc) {
        this.soCanCuoc = soCanCuoc;
    }

    public StringFilter getLoaiHo() {
        return loaiHo;
    }

    public StringFilter loaiHo() {
        if (loaiHo == null) {
            loaiHo = new StringFilter();
        }
        return loaiHo;
    }

    public void setLoaiHo(StringFilter loaiHo) {
        this.loaiHo = loaiHo;
    }

    public StringFilter getSoHoNgheo() {
        return soHoNgheo;
    }

    public StringFilter soHoNgheo() {
        if (soHoNgheo == null) {
            soHoNgheo = new StringFilter();
        }
        return soHoNgheo;
    }

    public void setSoHoNgheo(StringFilter soHoNgheo) {
        this.soHoNgheo = soHoNgheo;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getSdt() {
        return sdt;
    }

    public StringFilter sdt() {
        if (sdt == null) {
            sdt = new StringFilter();
        }
        return sdt;
    }

    public void setSdt(StringFilter sdt) {
        this.sdt = sdt;
    }

    public StringFilter getDiaChi() {
        return diaChi;
    }

    public StringFilter diaChi() {
        if (diaChi == null) {
            diaChi = new StringFilter();
        }
        return diaChi;
    }

    public void setDiaChi(StringFilter diaChi) {
        this.diaChi = diaChi;
    }

    public LongFilter getTaikhoanId() {
        return taikhoanId;
    }

    public LongFilter taikhoanId() {
        if (taikhoanId == null) {
            taikhoanId = new LongFilter();
        }
        return taikhoanId;
    }

    public void setTaikhoanId(LongFilter taikhoanId) {
        this.taikhoanId = taikhoanId;
    }

    public LongFilter getHoadongdId() {
        return hoadongdId;
    }

    public LongFilter hoadongdId() {
        if (hoadongdId == null) {
            hoadongdId = new LongFilter();
        }
        return hoadongdId;
    }

    public void setHoadongdId(LongFilter hoadongdId) {
        this.hoadongdId = hoadongdId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final HoGiaDinhCriteria that = (HoGiaDinhCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(tenChuHo, that.tenChuHo) &&
            Objects.equals(maHo, that.maHo) &&
            Objects.equals(soCanCuoc, that.soCanCuoc) &&
            Objects.equals(loaiHo, that.loaiHo) &&
            Objects.equals(soHoNgheo, that.soHoNgheo) &&
            Objects.equals(email, that.email) &&
            Objects.equals(sdt, that.sdt) &&
            Objects.equals(diaChi, that.diaChi) &&
            Objects.equals(taikhoanId, that.taikhoanId) &&
            Objects.equals(hoadongdId, that.hoadongdId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tenChuHo, maHo, soCanCuoc, loaiHo, soHoNgheo, email, sdt, diaChi, taikhoanId, hoadongdId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HoGiaDinhCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (tenChuHo != null ? "tenChuHo=" + tenChuHo + ", " : "") +
            (maHo != null ? "maHo=" + maHo + ", " : "") +
            (soCanCuoc != null ? "soCanCuoc=" + soCanCuoc + ", " : "") +
            (loaiHo != null ? "loaiHo=" + loaiHo + ", " : "") +
            (soHoNgheo != null ? "soHoNgheo=" + soHoNgheo + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (sdt != null ? "sdt=" + sdt + ", " : "") +
            (diaChi != null ? "diaChi=" + diaChi + ", " : "") +
            (taikhoanId != null ? "taikhoanId=" + taikhoanId + ", " : "") +
            (hoadongdId != null ? "hoadongdId=" + hoadongdId + ", " : "") +
            "}";
    }
}
