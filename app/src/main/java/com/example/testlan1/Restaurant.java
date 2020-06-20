package com.example.testlan1;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private int Ma;
    private String Ten;
    private String DiaChi;
    private double DanhGia;

    public Restaurant(int ma, String ten, String diaChi, double danhGia) {
        Ma = ma;
        Ten = ten;
        DiaChi = diaChi;
        DanhGia = danhGia;
    }

    public int getMa() {
        return Ma;
    }

    public String getTen() {
        return Ten;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public double getDanhGia() {
        return DanhGia;
    }

    public void setMa(int ma) {
        Ma = ma;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public void setDanhGia(double danhGia) {
        DanhGia = danhGia;
    }
}
