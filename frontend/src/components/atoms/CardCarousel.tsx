/* eslint-disable jsx-a11y/click-events-have-key-events */
/* eslint-disable jsx-a11y/no-static-element-interactions */
/* eslint-disable jsx-a11y/no-noninteractive-element-interactions */

'use client';

import React from 'react';
import { useRouter } from 'next/navigation';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';

function SampleNextArrow(props: { className: any; style: any; onClick: any }) {
  const { className, style, onClick } = props;
  return (
    <div
      className={className}
      style={{ ...style, display: 'block' }}
      onClick={onClick}
    />
  );
}

function SamplePrevArrow(props: { className: any; style: any; onClick: any }) {
  const { className, style, onClick } = props;
  return (
    <div
      className={className}
      style={{ ...style, display: 'block' }}
      onClick={onClick}
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
}

export default function Page({ data }: { data: CardData }) {
  const router = useRouter();

  const settings = {
    dots: false,
    arrow: true,
    infinite: false,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    centerMode: true,
    centerPadding: '60px', // 카드 간 간격
    nextArrow: (
      <SampleNextArrow
        className={undefined}
        style={undefined}
        onClick={undefined}
      />
    ),
    preArrow: (
      <SamplePrevArrow
        className={undefined}
        style={undefined}
        onClick={undefined}
      />
    ),
  };

  const handleClick = (e: any) => {
    e.preventDefault();
    router.push('/profit/card/regist');
  };

  return (
    <div className="w-56 h-32 cursor-pointer z-50">
      <Slider {...settings}>
<<<<<<< HEAD
        {
          props.data.map((cardImageList: {
            cardImage: string,
            company: string,
            id: string,
            name: string,
            type: string
          }, i: number) => {
            if (i === 0) {
              return (
                <>
                  <div>
                    <img className="mr-5 w-36 h-32 border-solid border-2 border-white shadow-md rounded-[10px]" src='/CardRegist.png' onClick={handleClick} />
                  </div>
                  <div key={i}>
                    <h3 className="origin-top-left rotate-90 w-32"><img className="border-solid border-2 border-white shadow-md rounded-[10px]" src={cardImageList.cardImage} /></h3>
                  </div>
                </>
              )
            } else {
              return (
                <>
                  <div key={i}>
                    <h3 className="origin-top-left rotate-90 w-32"><img className="border-solid border-2 border-white shadow-md rounded-[10px]" src={cardImageList.cardImage} /></h3>
                  </div>
                </>
              )
            }
          }
=======
        <div>
          <img
            className="w-36 h-32 border-solid border-2 border-white shadow-md rounded-[10px]"
            src="/CardRegist.png"
            onClick={handleClick}
            alt="카드등록"
          />
        </div>
        {data.map(
          (cardImageList: {
            cardImage: string;
            company: string;
            id: string;
            name: string;
            type: string;
          }) => (
            <div key={cardImageList.id}>
              <h3 className="origin-top-left rotate-90 w-32">
                <img
                  className="border-solid border-2 border-white shadow-md rounded-[10px]"
                  src={cardImageList.cardImage}
                  alt="카드사진"
                />
              </h3>
            </div>
>>>>>>> 11eb60a60a9f93efaec8bd0a0849d1501a0bf13b
          )
        )}
      </Slider>
    </div>
  );
}
