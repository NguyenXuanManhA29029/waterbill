package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A HoaDon.
 */
@Entity
@Table(name = "hoa_don")
public class HoaDon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ten_chu_ho")
    private String tenChuHo;

    @Column(name = "thang_su_dung")
    private Long thangSuDung;

    @Column(name = "chi_so_moi")
    private Long chiSoMoi;

    @Column(name = "chi_so_cu")
    private Long chiSoCu;

    @Column(name = "so_nuoc")
    private Long soNuoc;

    @Column(name = "thanh_tien")
    private Long thanhTien;

    @Column(name = "tien_thue")
    private Long tienThue;

    @Column(name = "tong_tien")
    private Long tongTien;

    @Column(name = "ngay_thanh_toan")
    private Instant ngayThanhToan;

    @Column(name = "trang_thai_thanh_toan")
    private String trangThaiThanhToan;

    @OneToOne
    @JoinColumn(unique = true)
    private Thue thue;

    @OneToOne
    @JoinColumn(unique = true)
    private BacTienHoNgheo bacHoNgheo;

    @OneToOne
    @JoinColumn(unique = true)
    private BacTienHoThuong bacHoThuong;

    @JsonIgnoreProperties(value = { "taikhoan", "hoadongd" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private HoGiaDinh giadinh;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HoaDon id(Long id) {
        this.id = id;
        return this;
    }

    public String getTenChuHo() {
        return this.tenChuHo;
    }

    public HoaDon tenChuHo(String tenChuHo) {
        this.tenChuHo = tenChuHo;
        return this;
    }

    public void setTenChuHo(String tenChuHo) {
        this.tenChuHo = tenChuHo;
    }

    public Long getThangSuDung() {
        return this.thangSuDung;
    }

    public HoaDon thangSuDung(Long thangSuDung) {
        this.thangSuDung = thangSuDung;
        return this;
    }

    public void setThangSuDung(Long thangSuDung) {
        this.thangSuDung = thangSuDung;
    }

    public Long getChiSoMoi() {
        return this.chiSoMoi;
    }

    public HoaDon chiSoMoi(Long chiSoMoi) {
        this.chiSoMoi = chiSoMoi;
        return this;
    }

    public void setChiSoMoi(Long chiSoMoi) {
        this.chiSoMoi = chiSoMoi;
    }

    public Long getChiSoCu() {
        return this.chiSoCu;
    }

    public HoaDon chiSoCu(Long chiSoCu) {
        this.chiSoCu = chiSoCu;
        return this;
    }

    public void setChiSoCu(Long chiSoCu) {
        this.chiSoCu = chiSoCu;
    }

    public Long getSoNuoc() {
        return this.soNuoc;
    }

    public HoaDon soNuoc(Long soNuoc) {
        this.soNuoc = soNuoc;
        return this;
    }

    public void setSoNuoc(Long soNuoc) {
        this.soNuoc = soNuoc;
    }

    public Long getThanhTien() {
        return this.thanhTien;
    }

    public HoaDon thanhTien(Long thanhTien) {
        this.thanhTien = thanhTien;
        return this;
    }

    public void setThanhTien(Long thanhTien) {
        this.thanhTien = thanhTien;
    }

    public Long getTienThue() {
        return this.tienThue;
    }

    public HoaDon tienThue(Long tienThue) {
        this.tienThue = tienThue;
        return this;
    }

    public void setTienThue(Long tienThue) {
        this.tienThue = tienThue;
    }

    public Long getTongTien() {
        return this.tongTien;
    }

    public HoaDon tongTien(Long tongTien) {
        this.tongTien = tongTien;
        return this;
    }

    public void setTongTien(Long tongTien) {
        this.tongTien = tongTien;
    }

    public Instant getNgayThanhToan() {
        return this.ngayThanhToan;
    }

    public HoaDon ngayThanhToan(Instant ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
        return this;
    }

    public void setNgayThanhToan(Instant ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public String getTrangThaiThanhToan() {
        return this.trangThaiThanhToan;
    }

    public HoaDon trangThaiThanhToan(String trangThaiThanhToan) {
        this.trangThaiThanhToan = trangThaiThanhToan;
        return this;
    }

    public void setTrangThaiThanhToan(String trangThaiThanhToan) {
        this.trangThaiThanhToan = trangThaiThanhToan;
    }

    public Thue getThue() {
        return this.thue;
    }

    public HoaDon thue(Thue thue) {
        this.setThue(thue);
        return this;
    }

    public void setThue(Thue thue) {
        this.thue = thue;
    }

    public BacTienHoNgheo getBacHoNgheo() {
        return this.bacHoNgheo;
    }

    public HoaDon bacHoNgheo(BacTienHoNgheo bacTienHoNgheo) {
        this.setBacHoNgheo(bacTienHoNgheo);
        return this;
    }

    public void setBacHoNgheo(BacTienHoNgheo bacTienHoNgheo) {
        this.bacHoNgheo = bacTienHoNgheo;
    }

    public BacTienHoThuong getBacHoThuong() {
        return this.bacHoThuong;
    }

    public HoaDon bacHoThuong(BacTienHoThuong bacTienHoThuong) {
        this.setBacHoThuong(bacTienHoThuong);
        return this;
    }

    public void setBacHoThuong(BacTienHoThuong bacTienHoThuong) {
        this.bacHoThuong = bacTienHoThuong;
    }

    public HoGiaDinh getGiadinh() {
        return this.giadinh;
    }

    public HoaDon giadinh(HoGiaDinh hoGiaDinh) {
        this.setGiadinh(hoGiaDinh);
        return this;
    }

    public void setGiadinh(HoGiaDinh hoGiaDinh) {
        this.giadinh = hoGiaDinh;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HoaDon)) {
            return false;
        }
        return id != null && id.equals(((HoaDon) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HoaDon{" +
            "id=" + getId() +
            ", tenChuHo='" + getTenChuHo() + "'" +
            ", thangSuDung=" + getThangSuDung() +
            ", chiSoMoi=" + getChiSoMoi() +
            ", chiSoCu=" + getChiSoCu() +
            ", soNuoc=" + getSoNuoc() +
            ", thanhTien=" + getThanhTien() +
            ", tienThue=" + getTienThue() +
            ", tongTien=" + getTongTien() +
            ", ngayThanhToan='" + getNgayThanhToan() + "'" +
            ", trangThaiThanhToan='" + getTrangThaiThanhToan() + "'" +
            "}";
    }
}
