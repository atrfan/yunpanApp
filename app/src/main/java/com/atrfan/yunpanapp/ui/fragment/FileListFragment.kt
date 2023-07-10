package com.atrfan.yunpanapp.ui.fragment

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.atrfan.yunpanapp.R
import com.atrfan.yunpanapp.databinding.FragmentFileListBinding
import com.atrfan.yunpanapp.network.pojo.Data
import com.atrfan.yunpanapp.network.request.FileRequest
import com.atrfan.yunpanapp.ui.activity.MainActivity
import com.atrfan.yunpanapp.ui.adapter.FileListAdapter
import com.atrfan.yunpanapp.ui.decoration.FileGridItemDecoration
import com.kongzue.dialogx.dialogs.WaitDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FileListFragment : Fragment() {

    private lateinit var binding: FragmentFileListBinding
    private var flag = true

    private val selectFileContract = ActivityResultContracts.GetContent()
    private val activityResultCallback = ActivityResultCallback<Uri?>{uri ->
        Log.d("FileListFragment",uri.toString())
        this.activity?.contentResolver?.let {
            // TODO 上传文件功能
            uploadFile(uri,it)
        }
    }
    private val launcher = registerForActivityResult(selectFileContract,activityResultCallback)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFileListBinding.inflate(inflater, container, false)
        loadingUi("/",MainActivity.user!!.username)
        binding.uploadButton.setOnClickListener {
            // 打开文件选择器
            launcher.launch("*/*")
        }
        return binding.root
    }

    private fun loadingUi(path: String, username: String) {
        CoroutineScope(Dispatchers.Main).launch {
            WaitDialog.show(R.string.loading)
            val fileList = withContext(Dispatchers.IO) {
                FileRequest.getFileList(path, username)
            }
            if (fileList == null) {
                WaitDialog.dismiss()
                Toast.makeText(
                    this@FileListFragment.context,
                    R.string.network_error,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                freshRecyclerView(fileList, flag)
                flag = false
                WaitDialog.dismiss()
            }
        }
    }

    private fun freshRecyclerView(fileList: List<Data>, flag: Boolean) {
        val recyclerView = binding.fileList
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = FileListAdapter(context, fileList)
        val largePadding = resources.getDimensionPixelSize(R.dimen.grid_spacing)
        val smallPadding = resources.getDimensionPixelSize(R.dimen.grid_spacing_small)
        if (flag) {
            recyclerView.addItemDecoration(
                FileGridItemDecoration(
                    largePadding,
                    smallPadding
                )
            )
        }
        WaitDialog.dismiss()
    }

    private fun uploadFile(uri:Uri,contentResolver: ContentResolver){
        CoroutineScope(Dispatchers.Main).launch {
            WaitDialog.show(R.string.uploading)
            val uploadResult = withContext(Dispatchers.IO){
                FileRequest.uploadFile("/",uri,contentResolver,MainActivity.user?.username.toString())
            }
            Log.d("FileListFragment",uploadResult.toString())
            WaitDialog.dismiss()

            loadingUi("/",MainActivity.user!!.username)
        }
    }

}
