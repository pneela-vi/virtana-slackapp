//package com.virtana.slackapp.graph.model;
//
////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by FernFlower decompiler)
////
//
//
//import com.slack.api.model.block.LayoutBlock;
//import com.slack.api.model.block.composition.PlainTextObject;
//import java.util.Objects;
//import lombok.Generated;
//
//public class ImageBlock implements LayoutBlock {
//    public static final String TYPE = "image";
//    private final String type = "image";
//    private String fallback;
//    private String image_url;
//    private Integer imageWidth;
//    private Integer imageHeight;
//    private Integer imageBytes;
//    private String altText;
//    private PlainTextObject title;
//    private String blockId;
//
//    @Generated
//    public static com.slack.api.model.block.ImageBlock.ImageBlockBuilder builder() {
//        return new com.slack.api.model.block.ImageBlock.ImageBlockBuilder();
//    }
//
//    @Generated
//    public String getType() {
//        Objects.requireNonNull(this);
//        return "image";
//    }
//
//    @Generated
//    public String getFallback() {
//        return this.fallback;
//    }
//
//    @Generated
//    public String getImage_url() {
//        return this.image_url;
//    }
//
//    @Generated
//    public Integer getImageWidth() {
//        return this.imageWidth;
//    }
//
//    @Generated
//    public Integer getImageHeight() {
//        return this.imageHeight;
//    }
//
//    @Generated
//    public Integer getImageBytes() {
//        return this.imageBytes;
//    }
//
//    @Generated
//    public String getAltText() {
//        return this.altText;
//    }
//
//    @Generated
//    public PlainTextObject getTitle() {
//        return this.title;
//    }
//
//    @Generated
//    public String getBlockId() {
//        return this.blockId;
//    }
//
//    @Generated
//    public void setFallback(String fallback) {
//        this.fallback = fallback;
//    }
//
//    @Generated
//    public void setImage_url(String image_url) {
//        this.image_url = image_url;
//    }
//
//    @Generated
//    public void setImageWidth(Integer imageWidth) {
//        this.imageWidth = imageWidth;
//    }
//
//    @Generated
//    public void setImageHeight(Integer imageHeight) {
//        this.imageHeight = imageHeight;
//    }
//
//    @Generated
//    public void setImageBytes(Integer imageBytes) {
//        this.imageBytes = imageBytes;
//    }
//
//    @Generated
//    public void setAltText(String altText) {
//        this.altText = altText;
//    }
//
//    @Generated
//    public void setTitle(PlainTextObject title) {
//        this.title = title;
//    }
//
//    @Generated
//    public void setBlockId(String blockId) {
//        this.blockId = blockId;
//    }
//
//    @Generated
//    public boolean equals(Object o) {
//        if (o == this) {
//            return true;
//        } else if (!(o instanceof com.slack.api.model.block.ImageBlock)) {
//            return false;
//        } else {
//            com.slack.api.model.block.ImageBlock other = (com.slack.api.model.block.ImageBlock)o;
//            if (!other.canEqual(this)) {
//                return false;
//            } else {
//                label119: {
//                    Object this$imageWidth = this.getImageWidth();
//                    Object other$imageWidth = other.getImageWidth();
//                    if (this$imageWidth == null) {
//                        if (other$imageWidth == null) {
//                            break label119;
//                        }
//                    } else if (this$imageWidth.equals(other$imageWidth)) {
//                        break label119;
//                    }
//
//                    return false;
//                }
//
//                Object this$imageHeight = this.getImageHeight();
//                Object other$imageHeight = other.getImageHeight();
//                if (this$imageHeight == null) {
//                    if (other$imageHeight != null) {
//                        return false;
//                    }
//                } else if (!this$imageHeight.equals(other$imageHeight)) {
//                    return false;
//                }
//
//                label105: {
//                    Object this$imageBytes = this.getImageBytes();
//                    Object other$imageBytes = other.getImageBytes();
//                    if (this$imageBytes == null) {
//                        if (other$imageBytes == null) {
//                            break label105;
//                        }
//                    } else if (this$imageBytes.equals(other$imageBytes)) {
//                        break label105;
//                    }
//
//                    return false;
//                }
//
//                Object this$type = this.getType();
//                Object other$type = other.getType();
//                if (this$type == null) {
//                    if (other$type != null) {
//                        return false;
//                    }
//                } else if (!this$type.equals(other$type)) {
//                    return false;
//                }
//
//                label91: {
//                    Object this$fallback = this.getFallback();
//                    Object other$fallback = other.getFallback();
//                    if (this$fallback == null) {
//                        if (other$fallback == null) {
//                            break label91;
//                        }
//                    } else if (this$fallback.equals(other$fallback)) {
//                        break label91;
//                    }
//
//                    return false;
//                }
//
//                Object this$imageUrl = this.getImage_url();
//                Object other$imageUrl = other.getImageUrl();
//                if (this$imageUrl == null) {
//                    if (other$imageUrl != null) {
//                        return false;
//                    }
//                } else if (!this$imageUrl.equals(other$imageUrl)) {
//                    return false;
//                }
//
//                label77: {
//                    Object this$altText = this.getAltText();
//                    Object other$altText = other.getAltText();
//                    if (this$altText == null) {
//                        if (other$altText == null) {
//                            break label77;
//                        }
//                    } else if (this$altText.equals(other$altText)) {
//                        break label77;
//                    }
//
//                    return false;
//                }
//
//                label70: {
//                    Object this$title = this.getTitle();
//                    Object other$title = other.getTitle();
//                    if (this$title == null) {
//                        if (other$title == null) {
//                            break label70;
//                        }
//                    } else if (this$title.equals(other$title)) {
//                        break label70;
//                    }
//
//                    return false;
//                }
//
//                Object this$blockId = this.getBlockId();
//                Object other$blockId = other.getBlockId();
//                if (this$blockId == null) {
//                    if (other$blockId != null) {
//                        return false;
//                    }
//                } else if (!this$blockId.equals(other$blockId)) {
//                    return false;
//                }
//
//                return true;
//            }
//        }
//    }
//
//    @Generated
//    protected boolean canEqual(Object other) {
//        return other instanceof com.slack.api.model.block.ImageBlock;
//    }
//
//    @Generated
//    public int hashCode() {
//        int PRIME = true;
//        int result = 1;
//        Object $imageWidth = this.getImageWidth();
//        result = result * 59 + ($imageWidth == null ? 43 : $imageWidth.hashCode());
//        Object $imageHeight = this.getImageHeight();
//        result = result * 59 + ($imageHeight == null ? 43 : $imageHeight.hashCode());
//        Object $imageBytes = this.getImageBytes();
//        result = result * 59 + ($imageBytes == null ? 43 : $imageBytes.hashCode());
//        Object $type = this.getType();
//        result = result * 59 + ($type == null ? 43 : $type.hashCode());
//        Object $fallback = this.getFallback();
//        result = result * 59 + ($fallback == null ? 43 : $fallback.hashCode());
//        Object $imageUrl = this.getImage_url();
//        result = result * 59 + ($imageUrl == null ? 43 : $imageUrl.hashCode());
//        Object $altText = this.getAltText();
//        result = result * 59 + ($altText == null ? 43 : $altText.hashCode());
//        Object $title = this.getTitle();
//        result = result * 59 + ($title == null ? 43 : $title.hashCode());
//        Object $blockId = this.getBlockId();
//        result = result * 59 + ($blockId == null ? 43 : $blockId.hashCode());
//        return result;
//    }
//
//    @Generated
//    public String toString() {
//        return "ImageBlock(type=" + this.getType() + ", fallback=" + this.getFallback() + ", imageUrl=" + this.getImage_url() + ", imageWidth=" + this.getImageWidth() + ", imageHeight=" + this.getImageHeight() + ", imageBytes=" + this.getImageBytes() + ", altText=" + this.getAltText() + ", title=" + this.getTitle() + ", blockId=" + this.getBlockId() + ")";
//    }
//
//    @Generated
//    public ImageBlock() {
//    }
//
//    @Generated
//    public ImageBlock(String fallback, String image_url, Integer imageWidth, Integer imageHeight, Integer imageBytes, String altText, PlainTextObject title, String blockId) {
//        this.fallback = fallback;
//        this.image_url = image_url;
//        this.imageWidth = imageWidth;
//        this.imageHeight = imageHeight;
//        this.imageBytes = imageBytes;
//        this.altText = altText;
//        this.title = title;
//        this.blockId = blockId;
//    }
//
//    @Generated
//    public static class ImageBlockBuilder {
//        @Generated
//        private String fallback;
//        @Generated
//        private String imageUrl;
//        @Generated
//        private Integer imageWidth;
//        @Generated
//        private Integer imageHeight;
//        @Generated
//        private Integer imageBytes;
//        @Generated
//        private String altText;
//        @Generated
//        private PlainTextObject title;
//        @Generated
//        private String blockId;
//
//        @Generated
//        ImageBlockBuilder() {
//        }
//
//        @Generated
//        public com.slack.api.model.block.ImageBlock.ImageBlockBuilder fallback(String fallback) {
//            this.fallback = fallback;
//            return this;
//        }
//
//        @Generated
//        public com.slack.api.model.block.ImageBlock.ImageBlockBuilder imageUrl(String imageUrl) {
//            this.imageUrl = imageUrl;
//            return this;
//        }
//
//        @Generated
//        public com.slack.api.model.block.ImageBlock.ImageBlockBuilder imageWidth(Integer imageWidth) {
//            this.imageWidth = imageWidth;
//            return this;
//        }
//
//        @Generated
//        public com.slack.api.model.block.ImageBlock.ImageBlockBuilder imageHeight(Integer imageHeight) {
//            this.imageHeight = imageHeight;
//            return this;
//        }
//
//        @Generated
//        public com.slack.api.model.block.ImageBlock.ImageBlockBuilder imageBytes(Integer imageBytes) {
//            this.imageBytes = imageBytes;
//            return this;
//        }
//
//        @Generated
//        public com.slack.api.model.block.ImageBlock.ImageBlockBuilder altText(String altText) {
//            this.altText = altText;
//            return this;
//        }
//
//        @Generated
//        public com.slack.api.model.block.ImageBlock.ImageBlockBuilder title(PlainTextObject title) {
//            this.title = title;
//            return this;
//        }
//
//        @Generated
//        public com.slack.api.model.block.ImageBlock.ImageBlockBuilder blockId(String blockId) {
//            this.blockId = blockId;
//            return this;
//        }
//
//        @Generated
//        public com.slack.api.model.block.ImageBlock build() {
//            return new com.slack.api.model.block.ImageBlock(this.fallback, this.imageUrl, this.imageWidth, this.imageHeight, this.imageBytes, this.altText, this.title, this.blockId);
//        }
//
//        @Generated
//        public String toString() {
//            return "ImageBlock.ImageBlockBuilder(fallback=" + this.fallback + ", imageUrl=" + this.imageUrl + ", imageWidth=" + this.imageWidth + ", imageHeight=" + this.imageHeight + ", imageBytes=" + this.imageBytes + ", altText=" + this.altText + ", title=" + this.title + ", blockId=" + this.blockId + ")";
//        }
//    }
//}
