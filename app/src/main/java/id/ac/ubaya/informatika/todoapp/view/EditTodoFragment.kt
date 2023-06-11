package id.ac.ubaya.informatika.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import id.ac.ubaya.informatika.todoapp.R
import id.ac.ubaya.informatika.todoapp.viewmodel.DetailTodoViewModel

class EditTodoFragment : Fragment() {
    private lateinit var viewModel: DetailTodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)

        view.findViewById<TextView>(R.id.txtJudulToDo).text = "Edit ToDo"
        view.findViewById<TextView>(R.id.btnAdd).text = "Save Changes"

        val uuid = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid
        viewModel.fetch(uuid)
        observeViewModel()

        view.findViewById<Button>(R.id.btnAdd).setOnClickListener {
            val radio = view.findViewById<RadioGroup>(R.id.radioGroupPriority).checkedRadioButtonId
            viewModel.update(view.findViewById<TextView>(R.id.txtTitle).text.toString(), view.findViewById<TextView>(R.id.txtNotes).text.toString(),
                2, uuid)
            Toast.makeText(view.context, "Todo updated", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()
        }


    }
    fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            view?.findViewById<EditText>(R.id.txtTitle)?.setText(it.title)
            view?.findViewById<EditText>(R.id.txtNotes)?.setText(it.notes)

            when (it.priority) {
                1 -> view?.findViewById<RadioButton>(R.id.radioLow)?.isChecked = true
                2 -> view?.findViewById<RadioButton>(R.id.radioMedium)?.isChecked = true
                else -> view?.findViewById<RadioButton>(R.id.radioHigh)?.isChecked = true
            }

        })
    }

}