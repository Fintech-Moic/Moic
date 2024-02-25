'use client';

import { useRouter } from 'next/navigation';
import TwinsButtonGroup from '@/components/molecules/TwinsButtonGroup';
import TitleSentence from '@/components/atoms/TitleSentence';

export default function Page() {
  const router = useRouter();
  const goToFindId = () => {
    router.push('/auth/find/id');
  };
  const goToFindPassword = () => {
    router.push('/auth/find/password');
  };

  return (
    <div className="h-full flex flex-col">
      <div className="h-1/4 flex items-center">
        <TitleSentence
          title="아이디 / 비밀번호 찾기"
          sentence="인증을 통해 계정 정보를 찾아보세요."
        />
      </div>
      <div className="h-3/4 flex flex-col justify-start items-center">
        <TwinsButtonGroup
          height="h-1/5"
          topTitle="아이디 찾기"
          topBg="bg-g4"
          onClicktop={goToFindId}
          bottomTitle="비밀번호 찾기"
          bottomBg="bg-y4"
          onClickbottom={goToFindPassword}
        />
      </div>
    </div>
  );
}
