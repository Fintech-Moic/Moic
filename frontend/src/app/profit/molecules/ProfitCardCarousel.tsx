/* eslint-disable jsx-a11y/click-events-have-key-events */
/* eslint-disable jsx-a11y/no-noninteractive-element-interactions */

'use client';

import React from 'react';
import { useRouter } from 'next/navigation';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import Image from 'next/image';
import { useSetAtom } from 'jotai';
import CarouselCardItem from '../atoms/CarouselCardItem';
import BlackLeftArrowIcon from '@/../public/assets/BlackLeftArrowIcon.svg';
import BlackRightArrowIcon from '@/../public/assets/BlackRightArrowIcon.svg';
import { cardDeleteModalAtom } from '@/store/atoms/modal';
import CardRegistImage from '@/../public/CardRegist.png';

function SampleNextArrow(props: { className: any; style: any; onClick: any }) {
  const { className, style, onClick } = props;
  return (
    <Image
      className={className}
      onClick={onClick}
      style={{
        ...style,
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
      }}
      src={BlackRightArrowIcon}
      alt="오른쪽화살표"
    />
  );
}

function SamplePrevArrow(props: { className: any; style: any; onClick: any }) {
  const { className, style, onClick } = props;
  return (
    <Image
      className={className}
      onClick={onClick}
      style={{
        ...style,
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
      }}
      src={BlackLeftArrowIcon}
      alt="왼쪽화살표"
    />
  );
}

interface DetailCardData {
  cardImage: string;
  company: string;
  id: string;
  name: string;
  type: string;
}
interface CardData {
  [x: string]: any;
  data: DetailCardData[];
  canDelete: boolean;
  onClickNext: () => void;
  onClickPrev: () => void;
}

export default function ProfitCardCarousel({
  data,
  canDelete,
  onClickNext,
  onClickPrev,
}: CardData) {
  const router = useRouter();
  const setOpenCardDeleteModal = useSetAtom(cardDeleteModalAtom);
  const settings = {
    dots: false,
    arrow: true,
    infinite: false,
    speed: 500,
    slidesToShow: 3,
    slidesToScroll: 1,
    nextArrow: (
      <SampleNextArrow
        className={undefined}
        style={undefined}
        onClick={undefined}
      />
    ),
    prevArrow: (
      <SamplePrevArrow
        className={undefined}
        style={undefined}
        onClick={undefined}
      />
    ),
    beforeChange: (prevIdx: number, newIdx: number) => {
      const diff = newIdx - prevIdx;
      if (diff === 1) onClickNext();
      else if (diff - 1) onClickPrev();
    },
  };

  const handleClickRegist = (e: any) => {
    e.preventDefault();
    router.push('/profit/card/regist');
  };

  const handleClickCard = (name: string) => {
    router.push(`/profit/card/detail/${name}`);
  };
  const handleClickCardDelete = (
    name: string,
    cardImage: string,
    company: string
  ) => {
    setOpenCardDeleteModal((prev: any) => ({
      ...prev,
      isOpen: true,
      deleteCardInfo: { name, cardImage, company },
    }));
  };
  return (
    <div className="w-80 h-32 cursor-pointer [&_.slick-slide]:w-20 [&_.slick-slide]:h-32 [&_.slick-slide]:relative">
      <Slider {...settings}>
        <h3>
          <Image
            width={80}
            height={128}
            src={CardRegistImage}
            alt="카드등록"
            className="border-solid border-2 border-white shadow-md rounded-[10px] transform"
            onClick={handleClickRegist}
          />
        </h3>

        {data.map(
          ({
            cardImage,
            id,
            name,
            company,
          }: {
            cardImage: string;
            id: string;
            name: string;
            company: string;
          }) => (
            <CarouselCardItem
              key={`CarouselCardItem_${id}`}
              canDelete={canDelete}
              cardImage={cardImage}
              onClick={() => handleClickCard(name)}
              onClickDelete={() =>
                handleClickCardDelete(name, cardImage, company)
              }
            />
          )
        )}
      </Slider>
    </div>
  );
}
