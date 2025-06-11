package com.my.kalkulatorbmi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    // Deklarasi elemen UI
    private TextInputEditText weightEditText;
    private TextInputEditText heightEditText;
    private Button calculateButton;
    private TextView bmiValueTextView;
    private TextView bmiCategoryTextView;
    private TextView errorMessageTextView;
    private LinearLayout resultContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi elemen UI dari layout
        weightEditText = findViewById(R.id.weightEditText);
        heightEditText = findViewById(R.id.heightEditText);
        calculateButton = findViewById(R.id.calculateButton);
        bmiValueTextView = findViewById(R.id.bmiValueTextView);
        bmiCategoryTextView = findViewById(R.id.bmiCategoryTextView);
        errorMessageTextView = findViewById(R.id.errorMessageTextView);
        resultContainer = findViewById(R.id.resultContainer);

        // Atur OnClickListener untuk tombol hitung
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });
    }

    /**
     * Metode untuk menghitung BMI dan menampilkan hasilnya.
     */
    private void calculateBMI() {
        // Sembunyikan pesan error sebelumnya
        errorMessageTextView.setVisibility(View.GONE);
        // Sembunyikan container hasil sebelumnya
        resultContainer.setVisibility(View.GONE);

        String weightStr = weightEditText.getText().toString();
        String heightStr = heightEditText.getText().toString();

        // Validasi input
        if (weightStr.isEmpty() || heightStr.isEmpty()) {
            errorMessageTextView.setText("Mohon masukkan berat dan tinggi.");
            errorMessageTextView.setVisibility(View.VISIBLE);
            return;
        }

        double weight;
        double heightCm;

        try {
            weight = Double.parseDouble(weightStr);
            heightCm = Double.parseDouble(heightStr);
        } catch (NumberFormatException e) {
            errorMessageTextView.setText("Mohon masukkan angka yang valid.");
            errorMessageTextView.setVisibility(View.VISIBLE);
            return;
        }

        if (weight <= 0 || heightCm <= 0) {
            errorMessageTextView.setText("Berat dan tinggi harus lebih besar dari nol.");
            errorMessageTextView.setVisibility(View.VISIBLE);
            return;
        }

        // Konversi tinggi dari sentimeter ke meter
        double heightMeters = heightCm / 100.0;

        // Hitung BMI: berat / (tinggi dalam meter)^2
        double bmi = weight / (heightMeters * heightMeters);

        // Format BMI menjadi dua angka desimal
        DecimalFormat df = new DecimalFormat("#.##");
        bmiValueTextView.setText(df.format(bmi));

        // Tentukan kategori BMI
        String category;
        int categoryColor; // Menggunakan int untuk referensi warna R.color.

        if (bmi < 18.5) {
            category = "Kekurangan Berat Badan";
            categoryColor = getResources().getColor(R.color.bmi_underweight, getTheme());
        } else if (bmi >= 18.5 && bmi <= 24.9) {
            category = "Berat Badan Normal";
            categoryColor = getResources().getColor(R.color.bmi_normal, getTheme());
        } else if (bmi >= 25.0 && bmi <= 29.9) {
            category = "Kelebihan Berat Badan";
            categoryColor = getResources().getColor(R.color.bmi_overweight, getTheme());
        } else { // bmi >= 30.0
            category = "Obesitas";
            categoryColor = getResources().getColor(R.color.bmi_obese, getTheme());
        }

        bmiCategoryTextView.setText("Kategori: " + category);
        bmiCategoryTextView.setTextColor(categoryColor);

        // Tampilkan container hasil
        resultContainer.setVisibility(View.VISIBLE);
    }
}