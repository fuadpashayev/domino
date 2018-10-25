package domine.domino


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.blist.view.*

class bluetoothAdapter(val list:ArrayList<String>,val context:Context,val main:View,val bList:View):RecyclerView.Adapter<BluetoothViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BluetoothViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cell = BluetoothViewHolder(layoutInflater.inflate(R.layout.blist,parent,false))
        return cell
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BluetoothViewHolder, position: Int) {
        val cell = holder.itemView
        val data = list[position]
        cell.bName.text = data
        cell.setOnClickListener {
            Toast.makeText(context,"$data klik olundu",Toast.LENGTH_SHORT).show()
            main.visibility = View.VISIBLE
            bList.visibility = View.GONE
        }
    }

}

class BluetoothViewHolder(view: View):RecyclerView.ViewHolder(view)