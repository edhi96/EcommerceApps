package tia.sarwoedhi.ecommerce.feat.receipt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tia.sarwoedhi.ecommerce.R
import tia.sarwoedhi.ecommerce.core.util.DateExt
import tia.sarwoedhi.ecommerce.core.util.UiState
import tia.sarwoedhi.ecommerce.core.util.toFormatDecimal
import tia.sarwoedhi.ecommerce.databinding.FragmentReceiptBinding
import tia.sarwoedhi.ecommerce.feat.receipt.adapter.OrderAdapter
import java.util.Date

@AndroidEntryPoint
class ReceiptFragment : Fragment() {

    private var _binding: FragmentReceiptBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReceiptViewModel by viewModels()
    private lateinit var orderAdapter: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReceiptBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModel.getLatestOrder()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        binding.topAppBar.lblTopBar.text = getString(R.string.order_success)
        binding.topAppBar.imgCart.isVisible = false
        binding.topAppBar.imgProfile.isVisible = false
        orderAdapter = OrderAdapter()
        with(binding.rvOrder) {
            this.adapter = orderAdapter
            this.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        binding.btnCompleted.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
    }

    private fun observeViewModel() {
        viewModel.order.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        ).onEach { state ->
            binding.progressBar.isVisible = state is UiState.Loading
            if (state is UiState.Success) {
                val orderTime = DateExt.getDateDDMMYYYYHHMMSS(Date(state.data?.orderTime ?: 0L))
                val totalAmount = state.data?.totalAmount?.toFormatDecimal().toString()

                binding.txtOrderDate.text = getString(R.string.order_time, orderTime)
                binding.txtTotalAmount.text = getString(R.string.order_time, totalAmount)
                if (state.data?.productOrder?.isNotEmpty() == true) {
                    orderAdapter.updateData(state.data.productOrder)
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.cart_empty), Toast.LENGTH_LONG
                    ).show()
                }
            }

            if (state is UiState.Error) {
                Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
            }
        }
            .launchIn(viewLifecycleOwner.lifecycleScope)

    }


}