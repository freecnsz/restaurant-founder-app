package com.freecnsz.whereToEat.ui.find


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.freecnsz.whereToEat.MainActivity
import com.freecnsz.whereToEat.R
import com.freecnsz.whereToEat.databinding.FragmentFindBinding
import com.freecnsz.whereToEat.databinding.ItemCardBinding
import com.freecnsz.whereToEat.ui.home.HomeFragment
import com.freecnsz.whereToEat.ui.profile.ProfileFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FindFragment : Fragment() {

    private var _binding: FragmentFindBinding? = null
    private val binding get() = _binding!!
    private val auth = Firebase.auth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflater.inflate(R.layout.fragment_find, container, false)
        _binding = FragmentFindBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.iconMenu.setOnClickListener { showPopup(it) }

        val cardDataList = createCardDataList()
        val adapter = CardAdapter(cardDataList)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            this.adapter = adapter
        }
    }

    private fun showPopup(view: View) {
        val popup = PopupMenu(context, view)
        popup.inflate(R.menu.options_menu)

        popup.setOnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.home -> {
                    val transaction = parentFragmentManager.beginTransaction()
                    transaction.replace(R.id.frameLayout, HomeFragment())
                    transaction.addToBackStack(null)
                    transaction.commit()
                }

                R.id.find -> {
                    val transaction = parentFragmentManager.beginTransaction()
                    transaction.replace(R.id.frameLayout, FindFragment())
                    transaction.addToBackStack(null)
                    transaction.commit()
                }

                R.id.profile -> {
                    val transaction = parentFragmentManager.beginTransaction()
                    transaction.replace(R.id.frameLayout, ProfileFragment())
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
                R.id.logOut -> {
                    auth.signOut()
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }
            }

            true
        }

        popup.show()
    }

    private fun createCardDataList(): List<CardData> {

        return listOf(
            CardData("Restaurant 1", R.drawable.logo_text, 350.0, 4.5),
            CardData("Restaurant 2", R.drawable.logo_text, 450.0, 3.0),
            CardData("Restaurant 3", R.drawable.logo_text, 150.0, 1.5),
            CardData("Restaurant 4", R.drawable.logo_text, 200.0, 5.0),
            CardData("Restaurant 5", R.drawable.logo_text, 100.0, 2.5),
            CardData("Restaurant 6", R.drawable.logo_text, 680.0, 3.5),
            CardData("Restaurant 7", R.drawable.logo_text, 720.0, 2.5),
            CardData("Restaurant 8", R.drawable.logo_text, 540.0, 4.5)
        )
    }

    private inner class CardAdapter(private val cardDataList: List<CardData>) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemCardBinding.inflate(inflater, parent, false)
            return CardViewHolder(binding)
        }

        override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
            val cardData = cardDataList[position]
            holder.bind(cardData)
        }

        override fun getItemCount(): Int {
            return cardDataList.size
        }

        inner class CardViewHolder(private val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
            val string = "Distance: "
            val km = "Km"
            fun bind(cardData: CardData) {
                binding.textViewName.text = cardData.title
                binding.imageViewPhoto.setImageResource(cardData.imageResId)
                binding.ratingBar.rating = cardData.rate.toFloat()
                binding.textViewDistance.text = string + cardData.distance.toString() + km
            }
        }
    }

    data class CardData(val title: String, val imageResId: Int, val distance: Double, val rate: Double)

}