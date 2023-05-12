package com.example.crudadmin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ProductDataAdapter(context: Context, private val productDataList: MutableList<ProductData>) : BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return productDataList.size
    }

    override fun getItem(position: Int): Any {
        return productDataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            view = inflater.inflate(R.layout.item_product_data, parent, false)
            holder = ViewHolder()
            holder.productNameTextView = view.findViewById(R.id.product_name_text_view)
            holder.productPriceTextView = view.findViewById(R.id.product_price_text_view)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val productData = productDataList[position]
        holder.productNameTextView?.text = productData.product
        holder.productPriceTextView?.text = productData.price

        return view!!
    }

    private class ViewHolder {
        var productNameTextView: TextView? = null
        var productPriceTextView: TextView? = null
    }
}
