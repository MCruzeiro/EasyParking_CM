package ipvc.estg.easyparking.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.easyparking.R
import ipvc.estg.easyparking.api.Parque

class ParqueAdapter(val parques: List<Parque>): RecyclerView.Adapter<ParquesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParquesViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.destinosline, parent, false)
        return ParquesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return parques.size
    }

    override fun onBindViewHolder(holder: ParquesViewHolder, position: Int) {
        return holder.bind(parques[position])

    }
}

class ParquesViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
    private val descr: TextView = itemView.findViewById(R.id.descrDestino)
    fun bind(parque: Parque) {
        descr.text = parque.nome_parque
    }

}