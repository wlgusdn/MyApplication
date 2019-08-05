package com.example.wlgusdn.myapplication;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.Locale;






public class ChatAdapter extends ArrayAdapter<ChatData> {

    SharedPreferences sf;

    private final android.icu.text.SimpleDateFormat mSimpleDateFormat = new android.icu.text.SimpleDateFormat("a h:mm", Locale.getDefault());

    private final static int TYPE_MY_SELF = 0;

    private final static int TYPE_ANOTHER = 1;

    private String mMyEmail;



    public ChatAdapter(Context context, int resource) {

        super(context, resource);


    }



    public void setEmail(String email) {

        mMyEmail = email;

    }



    private View setAnotherView(LayoutInflater inflater) {

        View convertView = inflater.inflate(R.layout.listitem_chat, null);

        ViewHolderAnother holder = new ViewHolderAnother();

        holder.bindView(convertView);

        convertView.setTag(holder);

        return convertView;

    }



    private View setMySelfView(LayoutInflater inflater) {

        View convertView = inflater.inflate(R.layout.listitem_chat_my, null);

        ViewHolderMySelf holder = new ViewHolderMySelf();

        holder.bindView(convertView);

        convertView.setTag(holder);

        return convertView;

    }



    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        int viewType = getItemViewType(position);

        LayoutInflater inflater = LayoutInflater.from(getContext());

        if (convertView == null) {

            if (viewType == TYPE_ANOTHER) {

                convertView = setAnotherView(inflater);

            } else {

                convertView = setMySelfView(inflater);

            }

        }



        if (convertView.getTag() instanceof ViewHolderAnother) {

            if (viewType != TYPE_ANOTHER) {

                convertView = setAnotherView(inflater);

            }

            ((ViewHolderAnother) convertView.getTag()).setData(position);

        } else {

            if (viewType != TYPE_MY_SELF) {

                convertView = setMySelfView(inflater);

            }

            ((ViewHolderMySelf) convertView.getTag()).setData(position);

        }



        return convertView;

    }





    @Override

    public int getViewTypeCount() {

        return 2;

    }



    @Override

    public int getItemViewType(int position) {

        String email = getItem(position).userName;

        if(email!=null) {
            if (MainActivity.Myname.equals(email)) {

                return TYPE_MY_SELF; // 나의 채팅내용

            } else {

                return TYPE_ANOTHER; // 상대방의 채팅내용

            }
        }
        else
        {
            return TYPE_ANOTHER;
        }

    }



    private class ViewHolderAnother {

        private ImageView mImgProfile;

        private TextView mTxtUserName;

        private TextView mTxtMessage;

        private TextView mTxtTime;



        private void bindView(View convertView) {

            mImgProfile = (ImageView) convertView.findViewById(R.id.img_profile);

            mTxtUserName = (TextView) convertView.findViewById(R.id.txt_userName);

            mTxtMessage = (TextView) convertView.findViewById(R.id.txt_message);

            mTxtTime = (TextView) convertView.findViewById(R.id.txt_time);

        }



        private void setData(int position) {

            ChatData chatData = getItem(position);


            mTxtUserName.setText(chatData.userName);

            mTxtMessage.setText(chatData.message);

            mTxtTime.setText(mSimpleDateFormat.format(chatData.time));

        }

    }



    private class ViewHolderMySelf {

        private TextView mTxtMessage;

        private TextView mTxtTime;



        private void bindView(View convertView) {

            mTxtMessage = (TextView) convertView.findViewById(R.id.txt_message);

            mTxtTime = (TextView) convertView.findViewById(R.id.txt_time);

        }



        private void setData(int position) {

            ChatData chatData = getItem(position);

            mTxtMessage.setText(chatData.message);

            mTxtTime.setText(mSimpleDateFormat.format(chatData.time));

        }

    }

}