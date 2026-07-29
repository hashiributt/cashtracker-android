package com.example.cashtracker;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;

public class ExportReportFragment extends Fragment {

    public ExportReportFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_export_report, container, false);

        Button btnPDF = view.findViewById(R.id.btn_export_pdf);
        Button btnCSV = view.findViewById(R.id.btn_export_csv);

        btnPDF.setOnClickListener(v -> exportToPDF());
        btnCSV.setOnClickListener(v -> exportToCSV());

        return view;
    }

    private void exportToPDF() {
        try {
            PdfDocument pdfDocument = new PdfDocument();
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
            PdfDocument.Page page = pdfDocument.startPage(pageInfo);
            Canvas canvas = page.getCanvas();

            Paint titlePaint = new Paint();
            titlePaint.setColor(Color.BLACK);
            titlePaint.setTextSize(20);
            canvas.drawText("CashTracker Financial Report", 30, 50, titlePaint);

            Paint bodyPaint = new Paint();
            bodyPaint.setColor(Color.DKGRAY);
            bodyPaint.setTextSize(14);

            int y = 100;
            float income = 1200;
            float savings = 450;
            float expenses = 620;
            float remaining = 130;

            canvas.drawText("Income: $" + income, 30, y, bodyPaint);
            canvas.drawText("Savings: $" + savings, 30, y + 30, bodyPaint);
            canvas.drawText("Expenses: $" + expenses, 30, y + 60, bodyPaint);
            canvas.drawText("Remaining: $" + remaining, 30, y + 90, bodyPaint);

            pdfDocument.finishPage(page);

            File dir = requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            File file = new File(dir, "cashtracker_report.pdf");
            FileOutputStream fos = new FileOutputStream(file);
            pdfDocument.writeTo(fos);
            pdfDocument.close();

            Toast.makeText(getContext(), "PDF exported to: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "PDF export failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void exportToCSV() {
        try {
            File dir = requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            File file = new File(dir, "cashtracker_export.csv");
            FileOutputStream fos = new FileOutputStream(file);
            String csv = "Section,Amount\n" +
                    "Income,1200\n" +
                    "Savings,450\n" +
                    "Expenses,620\n" +
                    "Remaining,130\n";
            fos.write(csv.getBytes());
            fos.close();
            Toast.makeText(getContext(), "CSV exported to " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "CSV export failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
