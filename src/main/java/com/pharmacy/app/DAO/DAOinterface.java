/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.pharmacy.app.DAO;

import java.util.ArrayList;

/**
 *
 * @author LENOVO
 * @param <T>
 */
public interface DAOinterface<T> {
    public boolean insert (T t);
    public boolean update (T t);
    public boolean delete (String t);
    public ArrayList<T> selectAll();
    public T selectByID(String t);
    public ArrayList<T> search(String t);
}
