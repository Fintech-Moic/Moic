'use client';

import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useState } from 'react';
import { useAtom } from 'jotai';
import NumberProgress from '../atoms/NumberProgress';
import ProfitCardCarousel from '../molecules/ProfitCardCarousel';
import { getMyCard, postCardDelete } from '@/api/card';
import CarouselTitleSentence from '@/components/atoms/CarouselTitleSentence';
import Modal from '@/components/atoms/Modal';
import TitleSentence from '@/components/atoms/TitleSentence';
import BothButtonGroup from '@/components/molecules/BothButtonGroup';
import { cardDeleteModalAtom } from '@/store/atoms/modal';

interface MyCardContainerProps {
  myCard: any;
}
/** 내 카드 조회 페이지에서, 현재 순서, 캐러셀, 카드사, 카드명 또는 빈 기프티콘 페이지임을 보여주는 organisms 컴포넌트
 * @param {any} myCard 내 카드 정보
 * @returns {JSX.Element} 컴포넌트 반환
 */
export default function MyCardContainer({ myCard }: MyCardContainerProps) {
  const queryClient = useQueryClient();
  const { data, isLoading } = useQuery({
    queryKey: ['getMyCard'],
    queryFn: () => getMyCard(),
    initialData: myCard,
  });
  const cardDeletMutation = useMutation({
    mutationFn: (name: string) => postCardDelete(name),
  });
  const [currentCardProgress, setCurrentCardProgress] = useState(1);
  const [{ isOpen, deleteCardInfo }, setOpenCardDeleteModal] =
    useAtom(cardDeleteModalAtom);
  const { cardList } = data.data;

  if (isLoading) <div>로딩중...</div>;

  const handleClickPrev = () => {
    if (currentCardProgress === 1) return;
    setCurrentCardProgress((prev) => prev - 1);
  };

  const handleClickNext = () => {
    if (currentCardProgress === cardList.length) return;
    setCurrentCardProgress((prev) => prev + 1);
  };

  const handleClickModalClose = () => {
    setOpenCardDeleteModal((prev) => ({
      ...prev,
      isOpen: false,
      deleteCardInfo: {},
    }));
  };

  const handleClickCardDelete = () => {
    cardDeletMutation.mutate(deleteCardInfo.name, {
      onSuccess: () => {
        setOpenCardDeleteModal((prev) => ({
          ...prev,
          isOpen: false,
          deleteCardInfo: {},
        }));
        queryClient.invalidateQueries(['getMyCard']);
      },
      onError() {
        setOpenCardDeleteModal((prev) => ({
          ...prev,
          isOpen: false,
          deleteCardInfo: {},
        }));
        alert('카드 삭제 실패! 다시, 시도해주세요');
      },
    });
  };

  return (
    <div className="flex justify-center items-center flex-col gap-5">
      {isOpen &&
        deleteCardInfo.name &&
        deleteCardInfo.company &&
        deleteCardInfo.cardImage && (
          <Modal>
            <div className="flex flex-col gap-16 justify-center items-center w-full h-full">
              <TitleSentence title="" sentence="이 카드를 삭제하실 건가요?" />
              <div className="flex flex-col justify-center items-center gap-4">
                <img
                  src={deleteCardInfo.cardImage}
                  alt="삭제카드이미지"
                  className="w-32 h-20"
                />
                <CarouselTitleSentence
                  firstTitle={deleteCardInfo.company}
                  secondTitle={deleteCardInfo.name}
                />
              </div>
              <BothButtonGroup
                leftTitle="뒤로가기"
                rightTitle="삭제하기"
                onClickLeft={handleClickModalClose}
                onClickRight={handleClickCardDelete}
              />
            </div>
          </Modal>
        )}
      <NumberProgress
        startTitle="카드 등록이 가능합니다!"
        maxLength={cardList.length}
        currentProgress={currentCardProgress}
      />
      <ProfitCardCarousel
        data={cardList}
        canDelete
        onClickNext={handleClickNext}
        onClickPrev={handleClickPrev}
      />
      <CarouselTitleSentence
        firstTitle={cardList[currentCardProgress - 1].company}
        secondTitle={cardList[currentCardProgress - 1].name}
      />
    </div>
  );
}