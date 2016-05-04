package com.core.widget;

import com.core.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

/**
 * @author miaoxin.ye
 * @createdate 2013-12-16 下午8:49:14
 * @Description: 自定义Dialog
 */
public class CoreDialog extends Dialog {
	
    public CoreDialog(Context context, int theme) {
        super(context, theme);
    }
 
    public CoreDialog(Context context) {
        super(context);
    }
 
    public static class Builder {
 
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private String neutralButtonText;
        private View contentView;
        private CoreDialog dialog;
 
        private DialogInterface.OnClickListener positiveButtonClickListener, negativeButtonClickListener,
        	neutralButtonClickListener;
 
        public Builder(Context context) {
            this.context = context;
        }
 
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }
 
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }
 
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }
        
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }
 
        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }
 
        public Builder setPositiveButton(int positiveButtonText,
                DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }
 
        public Builder setPositiveButton(String positiveButtonText,
                DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }
 
        public Builder setNegativeButton(int negativeButtonText,
                DialogInterface.OnClickListener listener) {
            this.negativeButtonText = (String) context.getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }
 
        public Builder setNegativeButton(String negativeButtonText,
                DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }
        
        public Builder setNeutralButton(int neutralButtonText,
                DialogInterface.OnClickListener listener) {
            this.neutralButtonText = (String) context.getText(neutralButtonText);
            this.neutralButtonClickListener = listener;
            return this;
        }
 
        public Builder neutralButtonText(String neutralButtonText,
                DialogInterface.OnClickListener listener) {
            this.neutralButtonText = negativeButtonText;
            this.neutralButtonClickListener = listener;
            return this;
        }
        
        /**
         * @Description: 普通Dialog
         * @return
         */
        public CoreDialog create(){
        	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            dialog = new CoreDialog(context, R.style.base_dialog);
            View layout = inflater.inflate(R.layout.core_dialog, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            ((TextView) layout.findViewById(R.id.scale_dialog_title)).setText(title);
            Button positiveBut=((Button) layout.findViewById(R.id.left_button));
            if (positiveButtonText != null) {
                positiveBut.setText(positiveButtonText);
                positiveBut.setOnClickListener(new View.OnClickListener() {
                	@Override
                    public void onClick(View v) {
                    	if (positiveButtonClickListener != null) {
                    		positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                    	}else{
                    		dialog.dismiss();
                    	}
                    }
                });
            } else {
            	positiveBut.setVisibility(View.GONE);
            }
            
            Button neutralBut=((Button) layout.findViewById(R.id.center_button));
            if (neutralButtonText != null) {
            	neutralBut.setText(neutralButtonText);
            	neutralBut.setVisibility(View.VISIBLE);
            	neutralBut.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                    	if (neutralButtonClickListener != null) {
                    		neutralButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                    	}else{
                    		dialog.dismiss();
                    	}
                    }
                });
            } else {
            	neutralBut.setVisibility( View.GONE);
            }
            
            Button negativeBut=((Button) layout.findViewById(R.id.right_button));
            if (negativeButtonText != null) {
            	negativeBut.setText(negativeButtonText);
            	negativeBut.setVisibility(View.VISIBLE);
            	negativeBut.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                    	if (negativeButtonClickListener != null) {
                    		negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                    	}else{
                    		dialog.dismiss();
                    	}
                    }
                });
            } else {
            	negativeBut.setVisibility( View.GONE);
            }
            TextView textView= (TextView) layout.findViewById(R.id.scale_dialog_message);
            if (message != null) {
            	textView.setText(message);
            } else if (contentView != null) {
            	ViewFlipper viewFlipper=(ViewFlipper) layout.findViewById(R.id.vf_one_id);
            	textView.setVisibility(View.GONE);
            	viewFlipper.addView(contentView);
            	viewFlipper.setVisibility(View.VISIBLE);
            }
            dialog.setContentView(layout);
            return dialog;
        }
 
        /**
         * @Description: 不可取消对话框
         * @return
         */
        public CoreDialog createUnCancelDialog() {
        	CoreDialog dialog= create();
        	dialog.setCancelable(false);
        	return dialog;
        }
    }
    
}