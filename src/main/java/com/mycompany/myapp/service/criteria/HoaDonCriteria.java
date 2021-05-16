package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.HoaDon} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.HoaDonResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /hoa-dons?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class HoaDonCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tenChuHo;

    private LongFilter thangSuDung;

    private LongFilter chiSoMoi;

    private LongFilter chiSoCu;

    private LongFilter soNuoc;

    private LongFilter thanhTien;

    private LongFilter tienThue;

    private LongFilter tongTien;

    private InstantFilter ngayThanhToan;

    private StringFilter trangThaiThanhToan;

    private LongFilter thueId;

    private LongFilter bacHoNgheoId;

    private LongFilter bacHoThuongId;

    private LongFilter giadinhId;

    public HoaDonCriteria() {}

    public HoaDonCriteria(HoaDonCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tenChuHo = other.tenChuHo == null ? null : other.tenChuHo.copy();
        this.thangSuDung = other.thangSuDung == null ? null : other.thangSuDung.copy();
        this.chiSoMoi = other.chiSoMoi == null ? null : other.chiSoMoi.copy();
        this.chiSoCu = other.chiSoCu == null ? null : other.chiSoCu.copy();
        this.soNuoc = other.soNuoc == null ? null : other.soNuoc.copy();
        this.thanhTien = other.thanhTien == null ? null : other.thanhTien.copy();
        this.tienThue = other.tienThue == null ? null : other.tienThue.copy();
        this.tongTien = other.tongTien == null ? null : other.tongTien.copy();
        this.ngayThanhToan = other.ngayThanhToan == null ? null : other.ngayThanhToan.copy();
        this.trangThaiThanhToan = other.trangThaiThanhToan == null ? null : other.trangThaiThanhToan.copy();
        this.thueId = other.thueId == null ? null : other.thueId.copy();
        this.bacHoNgheoId = other.bacHoNgheoId == null ? null : other.bacHoNgheoId.copy();
        this.bacHoThuongId = other.bacHoThuongId == null ? null : other.bacHoThuongId.copy();
        this.giadinhId = other.giadinhId == null ? null : other.giadinhId.copy();
    }

    @Override
    public HoaDonCriteria copy() {
        return new HoaDonCriteria(this);
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

    public LongFilter getThangSuDung() {
        return thangSuDung;
    }

    public LongFilter thangSuDung() {
        if (thangSuDung == null) {
            thangSuDung = new LongFilter();
        }
        return thangSuDung;
    }

    public void setThangSuDung(LongFilter thangSuDung) {
        this.thangSuDung = thangSuDung;
    }

    public LongFilter getChiSoMoi() {
        return chiSoMoi;
    }

    public LongFilter chiSoMoi() {
        if (chiSoMoi == null) {
            chiSoMoi = new LongFilter();
        }
        return chiSoMoi;
    }

    public void setChiSoMoi(LongFilter chiSoMoi) {
        this.chiSoMoi = chiSoMoi;
    }

    public LongFilter getChiSoCu() {
        return chiSoCu;
    }

    public LongFilter chiSoCu() {
        if (chiSoCu == null) {
            chiSoCu = new LongFilter();
        }
        return chiSoCu;
    }

    public void setChiSoCu(LongFilter chiSoCu) {
        this.chiSoCu = chiSoCu;
    }

    public LongFilter getSoNuoc() {
        return soNuoc;
    }

    public LongFilter soNuoc() {
        if (soNuoc == null) {
            soNuoc = new LongFilter();
        }
        return soNuoc;
    }

    public void setSoNuoc(LongFilter soNuoc) {
        this.soNuoc = soNuoc;
    }

    public LongFilter getThanhTien() {
        return thanhTien;
    }

    public LongFilter thanhTien() {
        if (thanhTien == null) {
            thanhTien = new LongFilter();
        }
        return thanhTien;
    }

    public void setThanhTien(LongFilter thanhTien) {
        this.thanhTien = thanhTien;
    }

    public LongFilter getTienThue() {
        return tienThue;
    }

    public LongFilter tienThue() {
        if (tienThue == null) {
            tienThue = new LongFilter();
        }
        return tienThue;
    }

    public void setTienThue(LongFilter tienThue) {
        this.tienThue = tienThue;
    }

    public LongFilter getTongTien() {
        return tongTien;
    }

    public LongFilter tongTien() {
        if (tongTien == null) {
            tongTien = new LongFilter();
        }
        return tongTien;
    }

    public void setTongTien(LongFilter tongTien) {
        this.tongTien = tongTien;
    }

    public InstantFilter getNgayThanhToan() {
        return ngayThanhToan;
    }

    public InstantFilter ngayThanhToan() {
        if (ngayThanhToan == null) {
            ngayThanhToan = new InstantFilter();
        }
        return ngayThanhToan;
    }

    public void setNgayThanhToan(InstantFilter ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public StringFilter getTrangThaiThanhToan() {
        return trangThaiThanhToan;
    }

    public StringFilter trangThaiThanhToan() {
        if (trangThaiThanhToan == null) {
            trangThaiThanhToan = new StringFilter();
        }
        return trangThaiThanhToan;
    }

    public void setTrangThaiThanhToan(StringFilter trangThaiThanhToan) {
        this.trangThaiThanhToan = trangThaiThanhToan;
    }

    public LongFilter getThueId() {
        return thueId;
    }

    public LongFilter thueId() {
        if (thueId == null) {
            thueId = new LongFilter();
        }
        return thueId;
    }

    public void setThueId(LongFilter thueId) {
        this.thueId = thueId;
    }

    public LongFilter getBacHoNgheoId() {
        return bacHoNgheoId;
    }

    public LongFilter bacHoNgheoId() {
        if (bacHoNgheoId == null) {
            bacHoNgheoId = new LongFilter();
        }
        return bacHoNgheoId;
    }

    public void setBacHoNgheoId(LongFilter bacHoNgheoId) {
        this.bacHoNgheoId = bacHoNgheoId;
    }

    public LongFilter getBacHoThuongId() {
        return bacHoThuongId;
    }

    public LongFilter bacHoThuongId() {
        if (bacHoThuongId == null) {
            bacHoThuongId = new LongFilter();
        }
        return bacHoThuongId;
    }

    public void setBacHoThuongId(LongFilter bacHoThuongId) {
        this.bacHoThuongId = bacHoThuongId;
    }

    public LongFilter getGiadinhId() {
        return giadinhId;
    }

    public LongFilter giadinhId() {
        if (giadinhId == null) {
            giadinhId = new LongFilter();
        }
        return giadinhId;
    }

    public void setGiadinhId(LongFilter giadinhId) {
        this.giadinhId = giadinhId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final HoaDonCriteria that = (HoaDonCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(tenChuHo, that.tenChuHo) &&
            Objects.equals(thangSuDung, that.thangSuDung) &&
            Objects.equals(chiSoMoi, that.chiSoMoi) &&
            Objects.equals(chiSoCu, that.chiSoCu) &&
            Objects.equals(soNuoc, that.soNuoc) &&
            Objects.equals(thanhTien, that.thanhTien) &&
            Objects.equals(tienThue, that.tienThue) &&
            Objects.equals(tongTien, that.tongTien) &&
            Objects.equals(ngayThanhToan, that.ngayThanhToan) &&
            Objects.equals(trangThaiThanhToan, that.trangThaiThanhToan) &&
            Objects.equals(thueId, that.thueId) &&
            Objects.equals(bacHoNgheoId, that.bacHoNgheoId) &&
            Objects.equals(bacHoThuongId, that.bacHoThuongId) &&
            Objects.equals(giadinhId, that.giadinhId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            tenChuHo,
            thangSuDung,
            chiSoMoi,
            chiSoCu,
            soNuoc,
            thanhTien,
            tienThue,
            tongTien,
            ngayThanhToan,
            trangThaiThanhToan,
            thueId,
            bacHoNgheoId,
            bacHoThuongId,
            giadinhId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HoaDonCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (tenChuHo != null ? "tenChuHo=" + tenChuHo + ", " : "") +
            (thangSuDung != null ? "thangSuDung=" + thangSuDung + ", " : "") +
            (chiSoMoi != null ? "chiSoMoi=" + chiSoMoi + ", " : "") +
            (chiSoCu != null ? "chiSoCu=" + chiSoCu + ", " : "") +
            (soNuoc != null ? "soNuoc=" + soNuoc + ", " : "") +
            (thanhTien != null ? "thanhTien=" + thanhTien + ", " : "") +
            (tienThue != null ? "tienThue=" + tienThue + ", " : "") +
            (tongTien != null ? "tongTien=" + tongTien + ", " : "") +
            (ngayThanhToan != null ? "ngayThanhToan=" + ngayThanhToan + ", " : "") +
            (trangThaiThanhToan != null ? "trangThaiThanhToan=" + trangThaiThanhToan + ", " : "") +
            (thueId != null ? "thueId=" + thueId + ", " : "") +
            (bacHoNgheoId != null ? "bacHoNgheoId=" + bacHoNgheoId + ", " : "") +
            (bacHoThuongId != null ? "bacHoThuongId=" + bacHoThuongId + ", " : "") +
            (giadinhId != null ? "giadinhId=" + giadinhId + ", " : "") +
            "}";
    }
}
