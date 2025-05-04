/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.Utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
//import java.awt.Font;

/**
 *
 * @author BOI QUAN
 */
public class PageNumber extends PdfPageEventHelper{
    private Font font;
    public PageNumber(Font font){
        this.font = font;
    }
    public void onEndPage(PdfWriter writer, Document document){
        PdfContentByte cb = writer.getDirectContent();
        Phrase footer = new Phrase("Trang - " + writer.getPageNumber(), font);
        ColumnText.showTextAligned(
                cb, 
                Element.ALIGN_RIGHT, 
                footer, 
                document.right() - document.rightMargin(),
                document.bottom() - 10, // Vị trí dưới cùng
                0);
    }
}
