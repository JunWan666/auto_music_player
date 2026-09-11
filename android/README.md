# 游戏自动演奏器 · Android 端

桌面版(auto_music_player)的安卓适配:识别乐谱 → 触摸注入演奏。
与桌面版"模拟物理键盘扫描码"不同,安卓端把音符映射为**屏幕琴键坐标**,通过
**系统无障碍手势接口(dispatchGesture)** 注入多点触摸事件实现演奏 ——
**免 root、免 adb、不依赖任何外部工具,安装即用**(仅需开启无障碍服务)。

> 仅供学习交流与个人娱乐,请遵守游戏用户协议,使用产生的风险自行承担。

## 架构映射(桌面版 → 安卓版)

| 桌面版(core/) | 安卓版(android/app/src/main/java/com/automusic/player/) | 说明 |
|---|---|---|
| parser.py | core/JianpuParser.kt | 简谱解析,协议一致 |
| recognizer.py | core/recognizer/ | OkHttp SSE 流式,prompt 复用 |
| database.py | core/db/ | Room(SQLite) |
| settings_store.py | core/settings/SettingsStore.kt | DataStore |
| keymap.py | core/KeyPointMap.kt | 音符 → **屏幕坐标**(归一化 0..1) |
| player.py | core/PlayerEngine.kt | 协程 + 绝对时钟调度 |
| keyboard_driver.py(SendInput) | input/TouchInjector.kt + AmpAccessibilityService | 无障碍手势注入 |
| gui/(PyQt6) | ui/(Jetpack Compose) | 暗色琥珀金主题对齐 |

## 环境要求

- Android Studio(Koala 及以上),内置 JDK 需 17(JBR 25 及以上会因 Gradle 8.7 不支持而失败,可安装 Temurin 17 并在 Gradle JVM 中选择)
- 真机 Android 8+(建议 11+;已实测华为 nova 11 / HarmonyOS 4.2)

## 构建

```bash
# 方式一:Android Studio 直接打开 android/ 目录,等待 Sync 后 Run
# 方式二:命令行(需 JDK 17)
cd android
./gradlew :app:assembleDebug     # 产物: app/build/outputs/apk/debug/app-debug.apk
```

## 首次使用(仅需一次)

1. 安装 APK 并打开
2. 「演奏」页 → 触摸注入卡 → **"去开启"**(直达服务详情页)→ 打开主开关并允许
3. 回 App 状态点变绿即可使用

## 使用流程

1. **标定**:导入游戏演奏界面截图(鸣潮/原神内置按真机实测的默认布局),
   微调 21 个琴键点位(或"四角推算"自动插值),保存;可用"测试点击"验证落点
2. **模型设置**:测试包首次启动已预置临时测试渠道；新增供应商保存后自动成为当前模型，也可点击「使用此模型」切换
3. **识别**(两种途径任选):
   - 在线识别:导入乐谱图 → 开始识别曲名、简谱与节奏 → 自动进入结构化校对 → 试听 → 保存入库
   - 外部 AI 识别(免配置):复制提示词 → 粘贴到任意外部 AI 工具并附上乐谱图片 → 在识别页粘贴返回结果 → 校对、试听并保存
   - 未配置模型时不会填入内置样例；会引导前往设置或复制提示词。AI 未识别到曲名时自动生成名称，保存前仍可编辑
   - 识别页默认展示曲名、音符、时值与休止统计；原始简谱按需展开。多行谱面未识别到休止时可按行补半拍停顿
   - AI 保留原谱分行，不再依据行内字距或歌词语义自动添加休止；可按谱行断层补半拍停顿并试听校对
   - 有歌词时会逐行、逐音符对齐显示；数量不匹配的行留空，避免歌词串位
   - 试听固定使用口琴近似音色，BPM 使用 0-300 滑块调整；0 BPM 时不允许试听或保存
   - 点击结构化音符块可在该音符前后插入半拍休止，也可直接删除，无需进入文本编辑
4. **乐谱库**:点击曲目进入详情，可编辑曲名、简谱、歌词和默认 BPM，并支持口琴试听、保存修改或进入演奏
5. **演奏**:选谱 → 选布局 → 调 BPM → 页面底部试听当前曲目 → 开始演奏 → 3 秒倒计时内切到游戏

> 当前测试 APK 按测试需求内置了一个限额供应商凭据，安装包可被反编译提取该凭据；正式发布前必须移除或轮换。

## 技术要点

- **注入通道**:无障碍 `dispatchGesture`,多 stroke 同一手势 = 多指和弦
  (`ACTION_DOWN`→按住→自动抬起由 stroke duration 表达),按屏幕绝对坐标路由到前台游戏窗口
- **坐标**:全部按整块显示器归一化存储(`maximumWindowMetrics`),演奏时换算真实像素;
  App 以悬浮小窗/分屏运行时坐标依然正确
- **时序**:协程 + 绝对时钟调度(`delayUntil`),每音符周期严格 = 时值 + gap,
  修正了桌面版"周期 = dur×hold + gap"的节奏偏快问题
- **多布局**:鸣潮/原神预设按真机截图圆点检测生成,支持自建布局与四角双线性插值
- **保活**:演奏期间启动前台服务(`specialUse`),防止进程被杀中断演奏

## 关于注入通道的历史

曾实现 Shizuku + `InputManager.injectInputEvent`(反射 hidden API + shell uid 转发)路径,
在华为 HarmonyOS 4.2 上被系统对抗策略拒绝(注入返回 false,adb 会话进程除外),
且需要外部激活;无障碍通道在同等场景下稳定工作,故最终仅保留无障碍通道,
App 完全自主运行。输入层接口(`dispatchChord`)保持中立,未来可插拔其他通道(Root/adb 等)。

## 已知边界

- 游戏版本更新可能改变琴键布局 → 到标定页重新标定即可
- 触摸屏多指上限一般 5~10 点,3 音和弦无压力;特大和弦暂不支持
- 无障碍手势由系统派发,极快节奏(BPM>200 连十六分)时可能引入轻微抖动
- 注入式触摸理论上可能被游戏检测,与桌面版同风险等级
