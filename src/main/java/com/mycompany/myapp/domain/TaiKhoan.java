package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A TaiKhoan.
 */
@Entity
@Table(name = "tai_khoan")
public class TaiKhoan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "pass_word")
    private String passWord;

    @JsonIgnoreProperties(value = { "taikhoan", "hoadongd" }, allowSetters = true)
    @OneToOne(mappedBy = "taikhoan")
    private HoGiaDinh hoGiaDinh;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaiKhoan id(Long id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return this.userName;
    }

    public TaiKhoan userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return this.passWord;
    }

    public TaiKhoan passWord(String passWord) {
        this.passWord = passWord;
        return this;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public HoGiaDinh getHoGiaDinh() {
        return this.hoGiaDinh;
    }

    public TaiKhoan hoGiaDinh(HoGiaDinh hoGiaDinh) {
        this.setHoGiaDinh(hoGiaDinh);
        return this;
    }

    public void setHoGiaDinh(HoGiaDinh hoGiaDinh) {
        if (this.hoGiaDinh != null) {
            this.hoGiaDinh.setTaikhoan(null);
        }
        if (hoGiaDinh != null) {
            hoGiaDinh.setTaikhoan(this);
        }
        this.hoGiaDinh = hoGiaDinh;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaiKhoan)) {
            return false;
        }
        return id != null && id.equals(((TaiKhoan) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaiKhoan{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", passWord='" + getPassWord() + "'" +
            "}";
    }
}
