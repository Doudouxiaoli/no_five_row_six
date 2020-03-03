package com.wx.no_five_row_six.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.wx.common.jackson.JacksonMapper;
import com.wx.common.spring.mvc.WebUtil;
import com.wx.no_five_row_six.common.Const;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 上传图片
 *
 * @author fanyongqian
 * 2016年10月31日
 */
@Controller
@RequestMapping(value = "/upPic")
public class UploadController {
    private final static Logger logger = LoggerFactory.getLogger(UploadController.class);

    /**
     * 上传图片(调整手机上传图片横向的问题)
     * gx-更新时间：2018-11-27
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"uploadPic"}, method = RequestMethod.POST)
    public JsonNode uploadPic(String base64img, MultipartFile file, String type) {
        String path = null;
        String folderPath = null;
        if (type != null && type.equals(Const.PIC_TYPE_ALBUM)) {
            // 专辑图片存储路径
            folderPath = Const.PIC_PATH_ALBUM;
        } else if(type!=null&&type.equals(Const.PIC_TYPE_DANCE)){
            //舞蹈图片存储路径
            folderPath = Const.PIC_PATH_DANCE;
        }else if (type != null && type.equals(Const.PIC_TYPE_CONCERT)) {
            //演唱会图片存储路径
            folderPath = Const.PIC_PATH_CONCERT;
        }else if (type != null && type.equals(Const.PIC_TYPE_CONCERT_PROGRAM)) {
            //演唱会节目图片存储路径
            folderPath = Const.PIC_PATH_CONCERT_PROGRAM;
        } else if (type != null && type.equals(Const.PIC_TYPE_MOLIVIDEO_MOVIE)) {
            //作品->电影图片存储路径
            folderPath = Const.PIC_PATH_MOLIVIDEO_MOVIE;
        }else if (type != null && type.equals(Const.PIC_TYPE_MOLIVIDEO_TV)) {
            //作品->电视剧图片存储路径
            folderPath = Const.PIC_PATH_MOLIVIDEO_TV;
        }else if (type != null && type.equals(Const.PIC_TYPE_MOLIVIDEO_VARIETY)) {
            //作品->综艺图片存储路径
            folderPath = Const.PIC_PATH_MOLIVIDEO_VARIETY;
        }else if (type != null && type.equals(Const.PIC_TYPE_ENDORSEMENT_FOOD)) {
            //代言->食物图片存储路径
            folderPath = Const.PIC_PATH_ENDORSEMENT_FOOD;
        } else if (type != null && type.equals(Const.PIC_TYPE_ENDORSEMENT_FOOD)) {
            //代言->食物图片存储路径
            folderPath = Const.PIC_PATH_ENDORSEMENT_FOOD;
        }else if (type != null && type.equals(Const.PIC_TYPE_ENDORSEMENT_MAKEUP)) {
            //代言->美妆图片存储路径
            folderPath = Const.PIC_PATH_ENDORSEMENT_MAKEUP;
        }else if (type != null && type.equals(Const.PIC_TYPE_ENDORSEMENT_CLOTHES)) {
            //代言->服饰图片存储路径
            folderPath = Const.PIC_PATH_ENDORSEMENT_CLOTHES;
        }else if (type != null && type.equals(Const.PIC_TYPE_ENDORSEMENT_LUXURY)) {
            //代言->轻奢品图片存储路径
            folderPath = Const.PIC_PATH_ENDORSEMENT_LUXURY;
        }else if (type != null && type.equals(Const.PIC_TYPE_ENDORSEMENT_GAME)) {
            //代言->游戏图片存储路径
            folderPath = Const.PIC_PATH_ENDORSEMENT_GAME;
        } else{
            // 默认类型：文章缩略图存储路径
            folderPath = Const.PIC_PATH_DEFAULT;
        }

        path = WebUtil.getRealPath(folderPath);
        String fileName = file.getOriginalFilename();
        System.out.println(path);// 要保存的路径
//         重命名
        String ext = this.getSuffix(fileName);
        fileName = System.currentTimeMillis() + "." + ext;
        File target = new File(path);
        if (!target.exists()) {
            target.mkdirs();
        }
        File targetFile = new File(path, fileName);
        // 保存
        BufferedImage bi = null;
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("上传图片时出错。", e);
            return JacksonMapper.newErrorInstance("上传图片时出错。");
        }
        return JacksonMapper.newDataInstance(folderPath + fileName);
    }

    // 获取文件后缀
    private String getSuffix(String filename) {
        String suffix = "";
        int pos = filename.lastIndexOf('.');
        if (pos > 0 && pos < filename.length() - 1) {
            suffix = filename.substring(pos + 1);
        }
        return suffix;
    }


    /**
     * 上传多媒体文件：音频 视频
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"uploadMultimediaFile"}, method = RequestMethod.POST)
    public JsonNode uploadMultimediaFile(MultipartFile file, String type) {
        if (file == null) {
            return JacksonMapper.newErrorInstance("请上传文件");
        }
        String fileName = file.getOriginalFilename();
        //重命名
        String ext = this.getSuffix(fileName);

        String folderPath = Const.FILE_PATH;
        if (StringUtils.isNotEmpty(type)) {
            if ("audio".equalsIgnoreCase(type)) {
                folderPath = Const.AUDIO_PATH;
            } else if ("video".equalsIgnoreCase(type)) {
                folderPath = Const.VIDEO_PATH;
                if (!"mp4".equalsIgnoreCase(ext)) {
                    return JacksonMapper.newErrorInstance("请上传MP4格式的视频！");
                }
            } else if ("resources".equalsIgnoreCase(type)) {
                folderPath = Const.RESOURCES_PATH;
            }
        }
        String path = WebUtil.getRealPath(folderPath);
        fileName = System.currentTimeMillis() + "";
        File target = new File(path);
        if (!target.exists()) {
            target.mkdirs();
        }
        File targetFile = new File(path, fileName + "-o." + ext);
        //返回路径
        String returnPath = folderPath + fileName + "-o." + ext;
//        //PPT 文件另存为
//        if ("ppt".equalsIgnoreCase(ext) && "pptx".equalsIgnoreCase(ext)) {
//            String pptPath = WebUtil.getRealPath(Const.PPT_PATH);
//        }
        //保存
        try {
            file.transferTo(targetFile);
            /*String ext = this.getSuffix(fileName);
            destFileName = new Date().getTime() + "." + ext;
            ImageUtil.resizeFixedWidth(targetFile.getAbsolutePath(), path + File.separator + destFileName, 200, 1F);
            targetFile.delete();*/

            if ("video".equals(type)) {
                // 原始视频文件 TODO
				/*String videoRealPath = path + "/" + fileName + "-o." + ext;
				// 压缩后视频文件
				String compressVideoRealPath = path + "/" + fileName + ".mp4";
				// 压缩转换视频
				String line = WebConst.FFMPEG_PATH + " -y -i " + videoRealPath
						+ " -ar 44100 -vcodec libx264 " + compressVideoRealPath;
				boolean ret = runCommand(line);
				if (ret) {
					returnPath = WebConst.VIDEO_PATH + fileName + ".mp4";// 视频
				} else {
					returnPath = WebConst.VIDEO_PATH + fileName + "-o." + ext;// 视频
				}*/
            } else if ("ppt".equals(type)) {
//                String pptBackupsPath = WebUtil.getRealPath(FileUploadTypeUtil.PPT_BACKUPS_PATH);
//                File pptBackupsFile = new File(pptBackupsPath);
//                if (!pptBackupsFile.exists()) {
//                    pptBackupsFile.mkdirs();
//                }
//                //拷贝到pdf转图片的目录下
//                FileUtils.copyFileToDirectory(targetFile, pptBackupsFile);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("上传多媒体文件时出错。", e);
            return JacksonMapper.newErrorInstance("上传多媒体文件时出错。");
        }
        return JacksonMapper.newDataInstance(returnPath);
    }


    /**
     * 上传多媒体文件：音频 视频
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"uploadPPT"}, method = RequestMethod.POST)
    public JsonNode uploadPpt(MultipartFile file) {
        if (file == null) {
            return JacksonMapper.newErrorInstance("请上传文件");
        }
        String fileName = file.getOriginalFilename();
        //重命名
        String ext = this.getSuffix(fileName);
        if (!ext.equalsIgnoreCase("ppt") && !ext.equalsIgnoreCase("pptx")) {
            return JacksonMapper.newErrorInstance("请上传PPT");
        }
        String folderPath = Const.PPT_PATH;
        String pptImgPath = Const.PPT_IMG_PATH;

        String path = WebUtil.getRealPath(folderPath);
        fileName = System.currentTimeMillis() + "";
        File target = new File(path);
        if (!target.exists()) {
            target.mkdirs();
        }
        File targetFile = new File(path, fileName + "." + ext);
        //返回路径
        String pptPath = folderPath + fileName + "." + ext;
        String imgPath = pptImgPath + fileName + "/";
        Map<String, String> returnPath = new HashMap<String, String>();
        returnPath.put("pptPath", pptPath);
        returnPath.put("imgPath", imgPath);
        //保存
        try {
            file.transferTo(targetFile);

            String pptFilePath = WebUtil.getRealPath(Const.PPT_FILE_PATH);
            File pptFile = new File(pptFilePath);
            if (!pptFile.exists()) {
                pptFile.mkdirs();
            }

            //如果转图片路径不存在创建PPT转图片路径
            String ImgPath = WebUtil.getRealPath(Const.PPT_IMG_PATH);
            File pptImgFile = new File(ImgPath);
            if (!pptImgFile.exists()) {
                pptImgFile.mkdirs();
            }

            FileUtils.copyFileToDirectory(targetFile, pptFile);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("上传PPT文件时出错。", e);
            return JacksonMapper.newErrorInstance("上传PPT文件时出错。");
        }
        return JacksonMapper.newDataInstance(returnPath);
    }


}