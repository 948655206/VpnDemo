@echo off
"D:\\Android SDK\\cmake\\3.22.1\\bin\\cmake.exe" ^
  "-HD:\\VPNDemo\\openvpn\\src\\main\\cpp" ^
  "-DCMAKE_SYSTEM_NAME=Android" ^
  "-DCMAKE_EXPORT_COMPILE_COMMANDS=ON" ^
  "-DCMAKE_SYSTEM_VERSION=21" ^
  "-DANDROID_PLATFORM=android-21" ^
  "-DANDROID_ABI=arm64-v8a" ^
  "-DCMAKE_ANDROID_ARCH_ABI=arm64-v8a" ^
  "-DANDROID_NDK=D:\\Android SDK\\ndk\\25.1.8937393" ^
  "-DCMAKE_ANDROID_NDK=D:\\Android SDK\\ndk\\25.1.8937393" ^
  "-DCMAKE_TOOLCHAIN_FILE=D:\\Android SDK\\ndk\\25.1.8937393\\build\\cmake\\android.toolchain.cmake" ^
  "-DCMAKE_MAKE_PROGRAM=D:\\Android SDK\\cmake\\3.22.1\\bin\\ninja.exe" ^
  "-DCMAKE_LIBRARY_OUTPUT_DIRECTORY=D:\\VPNDemo\\openvpn\\build\\intermediates\\cxx\\Debug\\6k4l154q\\obj\\arm64-v8a" ^
  "-DCMAKE_RUNTIME_OUTPUT_DIRECTORY=D:\\VPNDemo\\openvpn\\build\\intermediates\\cxx\\Debug\\6k4l154q\\obj\\arm64-v8a" ^
  "-DCMAKE_BUILD_TYPE=Debug" ^
  "-BD:\\VPNDemo\\openvpn\\.cxx\\Debug\\6k4l154q\\arm64-v8a" ^
  -GNinja
