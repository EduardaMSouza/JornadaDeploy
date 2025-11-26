package com.example.ProjetoJornadaMobile.service;

import com.example.ProjetoJornadaMobile.dto.response.ProjetoSemMotorredutorResponse;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;

@Service
public class PdfGeneratorService {

    public byte[] gerarPdfSemMotorredutor(ProjetoSemMotorredutorResponse response) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();
            document.add(new Paragraph("Relatório de Projeto - Sem Motorredutor"));
            document.add(new Paragraph("----------------------------------"));
            PdfPTable table = getPdfPTable(response);
            document.add(table);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF", e);

        } finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
        }

        return out.toByteArray();
    }

    private static PdfPTable getPdfPTable(ProjetoSemMotorredutorResponse response) {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.addCell("Capacidade Real:");
        table.addCell(response.getCapacidadeReal() + " t/h");
        table.addCell("Potência Necessária:");
        table.addCell(response.getPotenciaNecessaria() + " kW");
        table.addCell("Momento Máximo (Torque):");
        table.addCell(response.getMomentoMaximo() + " N.m");
        table.addCell("Comprimento da Correia:");
        table.addCell(response.getComprimentoCorreia() + " m");
        return table;
    }
}