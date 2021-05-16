package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A HoGiaDinh.
 */
@Entity
@Table(name = "ho_gia_dinh")
public class HoGiaDinh implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ten_chu_ho")
    private String tenChuHo;

    @Column(name = "ma_ho")
    private String maHo;

    @Column(name = "so_can_cuoc")
    private String soCanCuoc;

    @Column(name = "loai_ho")
    private String loaiHo;

    @Column(name = "so_ho_ngheo")
    private String soHoNgheo;

    @Column(name = "email")
    private String email;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "dia_chi")
    private String diaChi;

    @JsonIgnoreProperties(value = { "hoGiaDinh" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private TaiKhoan taikhoan;

    @JsonIgnoreProperties(value = { "thue", "bacHoNgheo", "bacHoThuong", "giadinh" }, allowSetters = true)
    @OneToOne(mappedBy = "giadinh")
    private HoaDon hoadongd;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HoGiaDinh id(Long id) {
        this.id = id;
        return this;
    }

    public String getTenChuHo() {
        return this.tenChuHo;
    }

    public HoGiaDinh tenChuHo(String tenChuHo) {
        this.tenChuHo = tenChuHo;
        return this;
    }

    public void setTenChuHo(String tenChuHo) {
        this.tenChuHo = tenChuHo;
    }

    public String getMaHo() {
        return this.maHo;
    }

    public HoGiaDinh maHo(String maHo) {
        this.maHo = maHo;
        return this;
    }

    public void setMaHo(String maHo) {
        this.maHo = maHo;
    }

    public String getSoCanCuoc() {
        return this.soCanCuoc;
    }

    public HoGiaDinh soCanCuoc(String soCanCuoc) {
        this.soCanCuoc = soCanCuoc;
        return this;
    }

    public void setSoCanCuoc(String soCanCuoc) {
        this.soCanCuoc = soCanCuoc;
    }

    public String getLoaiHo() {
        return this.loaiHo;
    }

    public HoGiaDinh loaiHo(String loaiHo) {
        this.loaiHo = loaiHo;
        return this;
    }

    public void setLoaiHo(String loaiHo) {
        this.loaiHo = loaiHo;
    }

    public String getSoHoNgheo() {
        return this.soHoNgheo;
    }

    public HoGiaDinh soHoNgheo(String soHoNgheo) {
        this.soHoNgheo = soHoNgheo;
        return this;
    }

    public void setSoHoNgheo(String soHoNgheo) {
        this.soHoNgheo = soHoNgheo;
    }

    public String getEmail() {
        return this.email;
    }

    public HoGiaDinh email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return this.sdt;
    }

    public HoGiaDinh sdt(String sdt) {
        this.sdt = sdt;
        return this;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return this.diaChi;
    }

    public HoGiaDinh diaChi(String diaChi) {
        this.diaChi = diaChi;
        return this;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public TaiKhoan getTaikhoan() {
        return this.taikhoan;
    }

    public HoGiaDinh taikhoan(TaiKhoan taiKhoan) {
        this.setTaikhoan(taiKhoan);
        return this;
    }

    public void setTaikhoan(TaiKhoan taiKhoan) {
        this.taikhoan = taiKhoan;
    }

    public HoaDon getHoadongd() {
        return this.hoadongd;
    }

    public HoGiaDinh hoadongd(HoaDon hoaDon) {
        this.setHoadongd(hoaDon);
        return this;
    }

    public void setHoadongd(HoaDon hoaDon) {
        if (this.hoadongd != null) {
            this.hoadongd.setGiadinh(null);
        }
        if (hoadongd != null) {
            hoadongd.setGiadinh(this);
        }
        this.hoadongd = hoaDon;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HoGiaDinh)) {
            return false;
        }
        return id != null && id.equals(((HoGiaDinh) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HoGiaDinh{" +
            "id=" + getId() +
            ", tenChuHo='" + getTenChuHo() + "'" +
            ", maHo='" + getMaHo() + "'" +
            ", soCanCuoc='" + getSoCanCuoc() + "'" +
            ", loaiHo='" + getLoaiHo() + "'" +
            ", soHoNgheo='" + getSoHoNgheo() + "'" +
            ", email='" + getEmail() + "'" +
            ", sdt='" + getSdt() + "'" +
            ", diaChi='" + getDiaChi() + "'" +
            "}";
    }
}
