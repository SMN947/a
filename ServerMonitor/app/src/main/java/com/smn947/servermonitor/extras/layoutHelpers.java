package com.smn947.servermonitor.extras;
import android.widget.TableLayout.*;
import android.widget.TableRow.*;
import android.widget.*;
import android.content.*;
import java.util.*;
import android.util.*;
import android.view.*;

public class layoutHelpers {
	public static abstract class Table {
		public static final LayoutParams o_wc = new TableLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
		public static final LayoutParams mp_wc = new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		public static final LayoutParams wc_wc = new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	}

	public static abstract class Tablerow {
		public static final TableRow.LayoutParams o_wc = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
		public static final TableRow.LayoutParams mp_wc = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		public static final TableRow.LayoutParams wc_wc = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	}
	
	public static abstract class Layout {
		public static final LayoutParams o_wc = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
		public static final LayoutParams mp_wc = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		public static final LayoutParams wc_wc = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	}

	public static TableLayout GenRows(HashMap<String, String> hm, Context ctx) {

		TableLayout Cuerpo = new TableLayout(ctx);
		Cuerpo.setLayoutParams(layoutHelpers.Table.mp_wc);
		Cuerpo.setStretchAllColumns(true);
		
		for (Map.Entry<String, String> me : hm.entrySet()) {
					
			TableRow totales = new TableRow(ctx);
			totales.setLayoutParams(layoutHelpers.Table.mp_wc);
			
			TextView texto_total = new TextView(ctx);
			texto_total.setText(me.getKey());
			//texto_total.setTextColor(Color.parseColor("#0000CC"));
			texto_total.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			texto_total.setGravity(Gravity.LEFT);
			totales.addView(texto_total, layoutHelpers.Tablerow.o_wc);

			TextView texto_total2 = new TextView(ctx);
			texto_total2.setText(me.getValue());
			//texto_total2.setTextColor(Color.parseColor("#ff00CC"));
			texto_total2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			texto_total2.setGravity(Gravity.RIGHT);
			totales.addView(texto_total2, layoutHelpers.Tablerow.o_wc);

			Cuerpo.addView(totales);
		}
		return Cuerpo;
	}
}
