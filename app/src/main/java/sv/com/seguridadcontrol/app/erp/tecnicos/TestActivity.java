package sv.com.seguridadcontrol.app.erp.tecnicos;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.rm.freedrawview.FreeDrawView;
import com.rm.freedrawview.PathRedoUndoCountChangeListener;

import sv.com.seguridadcontrol.app.MainActivity;
import sv.com.seguridadcontrol.app.R;

public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ColorPicker cp = new ColorPicker(this, 0, 0, 0);




    }
}
