import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.thefoodapp.R
import com.example.thefoodapp.foods

class FoodAdapter(var items: ArrayList<foods>, var clickListner: OnFoodItemClickListner ) : RecyclerView.Adapter<FoodViewHolder>() {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        var foodViewHolder: FoodViewHolder = FoodViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.homepage_layout,parent,false))
        return foodViewHolder
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
//        holder.fName?.text = items.get(position).fName
//        holder.lName?.text = items.get(position).lName
//        holder.logo.setImageResource(items.get(position).logo)
        holder.initialize(items[position], clickListner)
    }
}

class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    var upName = itemView.findViewById<TextView>(R.id.uploadername)
    var foodName = itemView.findViewById<TextView>(R.id.foodname)
    var logo =  itemView.findViewById<ImageView>(R.id.imgId)

    fun initialize(item: foods, action:OnFoodItemClickListner) {
        upName.text = item.uploaderName
        foodName.text = item.foodName
        logo.setImageResource(item.logo)

        itemView.setOnClickListener {
            action.onItemClick(item, adapterPosition)
        }
    }
}

interface OnFoodItemClickListner{
    fun onItemClick(item: foods, position: Int)
}