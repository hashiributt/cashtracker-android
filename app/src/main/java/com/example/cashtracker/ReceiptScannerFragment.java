package com.example.cashtracker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

public class ReceiptScannerFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imageReceiptPreview;
    private TextView textExtractedInfo;
    private Bitmap currentBitmap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receipt_scanner, container, false);

        imageReceiptPreview = view.findViewById(R.id.image_receipt_preview);
        textExtractedInfo = view.findViewById(R.id.text_extracted_info);
        MaterialButton btnCapture = view.findViewById(R.id.btn_capture_receipt);

        btnCapture.setOnClickListener(v -> openCamera());

        return view;
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            currentBitmap = (Bitmap) extras.get("data");
            imageReceiptPreview.setImageBitmap(currentBitmap);
            extractTextFromImage(currentBitmap);
        }
    }

    private void extractTextFromImage(Bitmap bitmap) {
        InputImage image = InputImage.fromBitmap(bitmap, 0);
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        recognizer.process(image)
                .addOnSuccessListener(result -> textExtractedInfo.setText(result.getText()))
                .addOnFailureListener(e -> textExtractedInfo.setText("Failed to extract text: " + e.getMessage()));
    }
}
