#!/usr/bin/env python3
"""
重命名 screenshots/ 目录中的真实截图。
将 图片1.png... 重命名为有意义的英文名。
"""

import os
import shutil
from pathlib import Path

BASE_DIR = Path(__file__).resolve().parent.parent
SCREENSHOTS_DIR = BASE_DIR / 'screenshots'
REAL_DIR = SCREENSHOTS_DIR / 'real'

# 根据文件大小和特征映射图片到功能名称
IMAGE_MAPPING = {
    '图片1.png':   'producer-page.png',
    '图片2.png':   'supplier-page.png',
    '图片4.png':   'retailer-page.png',
    '图片5.png':   'trace-query.png',
    '图片6.png':   'trace-detail.png',
    '图片7.png':   'webase-deploy.png',
    '图片8.png':   'idea-backend.png',
    '图片9.png':   'vue-frontend.png',
    '图片10.png':  'supplier-form.png',
    '图片11.png':  'retailer-form.png',
    '图片12.png':  'producer-form.png',
    '图片13.png':  'product-list.png',
    '图片14.png':  'block-info.png',
    '图片15.png':  'contract-detail.png',
    '图片16.png':  'db-config.png',
    '图片17.png':  'system-config.png',
    '图片18.png':  'api-test.png',
    '图片19.png':  'contract-source.png',
    '图片20.png':  'trace-full.png',
}

def main():
    # 创建 real 目录
    REAL_DIR.mkdir(parents=True, exist_ok=True)
    print(f'📁 目标目录: {REAL_DIR}/')
    
    # 获取所有 PNG 文件
    png_files = sorted(SCREENSHOTS_DIR.glob('图片*.png'))
    print(f'📸 找到 {len(png_files)} 张截图\n')
    
    copied = 0
    for png_file in png_files:
        new_name = IMAGE_MAPPING.get(png_file.name)
        if new_name:
            dest = REAL_DIR / new_name
            shutil.copy2(png_file, dest)
            size_kb = dest.stat().st_size / 1024
            print(f'  ✅ {png_file.name} → {new_name}  ({size_kb:.0f} KB)')
            copied += 1
        else:
            print(f'  ⚠️  {png_file.name} 未映射，跳过')
    
    print(f'\n🎉 完成! 共复制 {copied} 张截图到 screenshots/real/')

if __name__ == '__main__':
    main()
