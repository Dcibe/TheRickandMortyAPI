package com.example.therickandmortyapi.ui

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import com.example.therickandmortyapi.R

class CustomProgressDialog(context: Context) {
    // Variables privadas
    private var dialog: CustomDialog
    private var cpTitle: TextView
    private var cpCardView: CardView
    private var progressBar: ProgressBar

    // Método para iniciar el cuadro de diálogo de progreso
    fun start(title: String = "") {
        cpTitle.text = title
        dialog.show()
    }

    // Método para detener el cuadro de diálogo de progreso
    fun stop() {
        dialog.dismiss()
    }

    // Bloque de inicialización, se ejecuta al crear una instancia de la clase
    init {
        // Inflar la vista del cuadro de diálogo de progreso desde el archivo de diseño XML
        val inflater = (context as Activity).layoutInflater
        val view = inflater.inflate(R.layout.progress_dialog, null)

        // Obtener referencias a los elementos de la vista
        cpTitle = view.findViewById(R.id.cp_title)
        cpCardView = view.findViewById(R.id.cp_cardview)
        progressBar = view.findViewById(R.id.cp_pbar)

        // Configurar el color de fondo de la tarjeta del cuadro de diálogo
        cpCardView.setCardBackgroundColor(Color.parseColor("#70000000"))

        // Configurar el color de la barra de progreso
        setColorFilter(
            progressBar.indeterminateDrawable,
            ResourcesCompat.getColor(context.resources, R.color.purple_500, null)
        )

        // Configurar el color del texto
        cpTitle.setTextColor(Color.WHITE)

        // Inicializar el cuadro de diálogo personalizado
        dialog = CustomDialog(context)
        dialog.setContentView(view)
    }

    // Método privado para aplicar un filtro de color a un drawable
    private fun setColorFilter(drawable: Drawable, color: Int) {
        // Verificar la versión de Android para utilizar el método apropiado de aplicar el filtro de color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            @Suppress("DEPRECATION")
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    // Clase interna que extiende la clase Dialog para personalizar el cuadro de diálogo
    class CustomDialog(context: Context) : Dialog(context, R.style.CustomDialogTheme) {
        init {
            // Establecer un color semitransparente para el fondo del cuadro de diálogo
            window?.decorView?.rootView?.setBackgroundResource(R.color.purple_200)
            // Consumir los márgenes de la ventana del sistema
            window?.decorView?.setOnApplyWindowInsetsListener { _, insets ->
                insets.consumeSystemWindowInsets()
            }
        }
    }
}
