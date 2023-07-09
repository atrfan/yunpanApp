package com.atrfan.yunpanapp.ui.adapter

import android.content.Context
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atrfan.yunpanapp.R
import com.atrfan.yunpanapp.databinding.FileItemBinding
import com.atrfan.yunpanapp.network.pojo.Data
import com.atrfan.yunpanapp.network.request.FileRequest
import com.atrfan.yunpanapp.ui.activity.MainActivity
import com.bumptech.glide.Glide
import com.kongzue.dialogx.DialogX
import com.kongzue.dialogx.dialogs.MessageDialog
import com.kongzue.dialogx.dialogs.TipDialog
import com.kongzue.dialogx.dialogs.WaitDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.Okio
import java.io.File

class FileListAdapter(private val parent: Context?, private val fileList: List<Data>) :
    RecyclerView.Adapter<FileListAdapter.FileListViewHolder>() {
    inner class FileListViewHolder(val binding: FileItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileListViewHolder {
        val binding = FileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FileListViewHolder(binding)
    }

    override fun getItemCount(): Int = fileList.size

    override fun onBindViewHolder(holder: FileListViewHolder, position: Int) {
        val binding = holder.binding
        val file = fileList[position]
        binding.fileName.text = file.fileName
        when (file.fileType) {
            "folder" -> Glide.with(binding.root).load(R.mipmap.folder).fitCenter()
                .into(binding.fileItemIcon)

            else -> Glide.with(binding.root).load(R.mipmap.file).fitCenter()
                .into(binding.fileItemIcon)
        }

        // 点击事件
        if(file.fileType != "folder"){
            binding.root.setOnClickListener {
                val fileMessage = "文件名：\n${file.fileName}\n文件大小：\n${file.fileSize}\n文件上传时间：\n${file.lastTime}"
                MessageDialog.build()
                    .setTheme(DialogX.THEME.DARK)
                    .setTitle(R.string.download_file)
                    .setMessage(fileMessage)
                    .setOkButton("确定"
                    ) { _, _ ->
                        downloadFile(file.currentPath, file.fileName, MainActivity.user!!.username)
                        false
                    }
                    .setCancelButton("取消")
                    .show()
            }
        }
    }

    private fun downloadFile(currentPath:String, downPath:String, username:String){
        CoroutineScope(Dispatchers.Main).launch {
            WaitDialog.show(R.string.downloading)
            val flag = withContext(Dispatchers.IO){
                // 判断是否有sd卡
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // 获取相对路径
                    val fileCurrentPath = this@FileListAdapter.parent?.getExternalFilesDir(null)?.path
                    // 拼接为绝对路径
                    val filepath = "$fileCurrentPath/$username"
                    // 判断是否存在这个文件目录，如果不存在就创建一个
                    val fileDir = File(filepath)
                    if(!fileDir.exists()){
                        fileDir.mkdir()
                    }
                    Log.d("DownloadAdapter",filepath)
                    // 下载文件
                    val source = FileRequest.downloadFile(currentPath, downPath, username)
                    val file = File("$filepath/$downPath")
                    val buffer = Okio.buffer(Okio.sink(file))
                    buffer.writeAll(source!!)
                    buffer.close()
                    true
                } else {
                    false
                }
            }
            WaitDialog.dismiss()
            if(flag){
                TipDialog.show("Success!", WaitDialog.TYPE.SUCCESS,1000);
            } else {
                TipDialog.show("Failure!", WaitDialog.TYPE.ERROR,1000);
            }
        }
    }
}