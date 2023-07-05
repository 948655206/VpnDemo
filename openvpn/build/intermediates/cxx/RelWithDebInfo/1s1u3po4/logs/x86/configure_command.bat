@echo off
"D:\\Android SDK\\cmake\\3.22.1\\bin\\cmake.exe" ^
  "-HD:\\VPNDemo\\openvpn\\src\\main\\cpp" ^
  "-DCMAKE_SYSTEM_NAME=Android" ^
  "-DCMAKE_EXPORT_COMPILE_COMMANDS=ON" ^
  "-DCMAKE_SYSTEM_VERSION=21" ^
  "-DANDROID_PLATFORM=android-21" ^
  "-DANDROID_ABI=x86" ^
  "-DCMAKE_ANDROID_ARCH_ABI=x86" ^
  "-DANDROID_NDK=D:\\Android SDK\\ndk\\25.1.8937393" ^
  "-DCMAKE_ANDROID_NDK=D:\\Android SDK\\ndk\\25.1.8937393" ^
  "-DCMAKE_TOOLCHAIN_FILE=D:\\Android SDK\\ndk\\25.1.8937393\\build\\cmake\\android.toolchain.cmake" ^
  "-DCMAKE_MAKE_PROGRAM=D:\\Android SDK\\cmake\\3.22.1\\bin\\ninja.exe" ^
  "-DCMAKE_LIBRARY_OUTPUT_DIRECTORY=D:\\VPNDemo\\openvpn\\build\\intermediates\\cxx\\RelWithDebInfo\\1s1u3po4\\obj\\x86" ^
  "-DCMAKE_RUNTIME_OUTPUT_DIRECTORY=D:\\VPNDemo\\openvpn\\build\\intermediates\\cxx\\RelWithDebInfo\\1s1u3po4\\obj\\x86" ^
  "-DCMAKE_BUILD_TYPE=RelWithDebInfo" ^
  "-BD:\\VPNDemo\\openvpn\\.cxx\\RelWithDebInfo\\1s1u3po4\\x86" ^
  -GNinja
