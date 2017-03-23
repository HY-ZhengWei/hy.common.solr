package org.hy.common.solr.junit;

import java.awt.image.BufferedImage;

import jp.sourceforge.qrcode.data.QRCodeImage;

/**
 * <strong>Description:<br/></strong>
 * 二维码图片对象
 * @author zhoubang
 * 创建时间：2014年9月19日 上午11:40:26
 */
public class TwoDimensionCodeImage implements QRCodeImage{
    BufferedImage bufImg;  
    
    public TwoDimensionCodeImage(BufferedImage bufImg) {  
        this.bufImg = bufImg;  
    }
      
    @Override  
    public int getHeight() {  
        return bufImg.getHeight();  
    }
  
    @Override  
    public int getPixel(int x, int y) {  
        return bufImg.getRGB(x, y);  
    }
  
    @Override  
    public int getWidth() {  
        return bufImg.getWidth();  
    }
}
