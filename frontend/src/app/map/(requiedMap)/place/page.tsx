/* eslint-disable @typescript-eslint/no-unused-vars */

'use client';

import React, { FormEvent, useEffect, useState } from 'react';
import { useAtomValue } from 'jotai';
import { Map, MapMarker, Polyline } from 'react-kakao-maps-sdk';
import Image from 'next/image';
import SearchBox from '../../molecules/FunctionalSearchBox';
import curLocAtom from '@/store/atoms/curLocAtom';
import searchResultAtom from '@/store/atoms/searchResultAtom';
// import { getSearchedPlace, getLogoImage, getDirection } from '@/api/map';
import { getSearchedPlace, getDirection, getBenefit } from '@/api/map';
import { fetchProfile } from '@/api/myPage';
import CardCarousel from '@/components/atoms/CardCarousel';

export default function Page() {
  const searchResult = useAtomValue(searchResultAtom);
  const curLoc = useAtomValue<any>(curLocAtom);
  const [shopLocs, setShopLocs] = useState([]);
  // const [shopLogo, setShopLogo] = useState([])
  const [selectedShop, setSelectedShop] = useState<any>(null);
  const [showDetails, setShowDetails] = useState(false);
  const [benefitInfo, setBenefitInfo] = useState<any>([]);
  const [userId, setUserId] = useState('');
  const [mapPath, setMapPath] = useState([]);
  const [far, setFar] = useState('');

  useEffect(() => {
    alert('Finished loading');
  }, []);

  const ResultClickEvent = async (result: string) => {
    try {
      const data = await getSearchedPlace(result, curLoc.lat, curLoc.lng);
      // const logo = await getLogoImage(); 로고 API 사용 여부 확정 후 주석 해제
      setShopLocs(data.data.shopList);
      // setShopLogo(logo) 로고 API 사용 여부 확정 후 주석 해제
    } catch (error) {
      console.error('가맹점 정보 불러오기 실패', error);
    }

    try {
      const data = await fetchProfile();
      setUserId(data.data.name);
    } catch (error) {
      console.error('유저 아이디 불러오기 실패', error);
    }
  };

  // interface LinePath {
  //   La: number;
  //   Ma: number;
  // }

  // interface Posts {
  //   props: {
  //     duration: number;
  //     linePath: LinePath[];
  //   };
  // }

  const handleMarkerClick = async (shop: any) => {
    setSelectedShop(shop);

    try {
      const str = { lat: curLoc.lat, lng: curLoc.lng };
      const fin = { lat: shop.latitude, lng: shop.longitude };
      const posts: any = await getDirection(str, fin);
      const distance: any = posts.props.howfar/10
      setFar(distance);

      const newMapPath = posts.props.linePath.map((item: any) => ({
        lng: item.La,
        lat: item.Ma,
      }));
      setMapPath(newMapPath);
    } catch (error) {
      console.error('경로 정보 불러오기 실패', error);
    }
  };

  interface DetailCardData {
    cardName: string;
    cashBack: string | null;
    content: string | null;
    discount: string | null;
    point: string | null;
  }

  interface CardData {
    [x: string]: any;
    data: DetailCardData[];
  }

  const BenefitClick = async (shop: {
    shopName: string;
    shopLocation: string;
  }) => {
    setShowDetails(!showDetails);
    try {
      const MyBenefitInfo = await getBenefit(shop.shopName, shop.shopLocation);
      const data: CardData = MyBenefitInfo.data.benefits;
      setBenefitInfo(data);
    } catch (error) {
      console.error('혜택 정보 불러오기 실패');
    }
  };

  return (
    <>
      <Map
        center={{
          lat: 37.50135,
          lng: 127.0397,
        }} // 멀티캠퍼스 역삼 주소 (기본 주소)
        style={{
          width: '100vw',
          height: '100vh',
          zIndex: '-1',
          position: 'absolute',
        }}
        level={4}
      >
        <div className="mt-7 flex justify-center">
          <SearchBox
            onSubmit={(event: FormEvent<HTMLFormElement>) => {
              throw new Error('검색 과정에서 오류가 발생하였습니다.');
            }}
          />
        </div>

        <Polyline
          path={[mapPath]}
          strokeWeight={5}
          strokeColor="#2EC4B6"
          strokeOpacity={0.9}
          strokeStyle="solid"
        />

        <div className="bg-white shadow-md rounded-[10px] w-10/12 mx-auto mt-2 font-suit text-xl">
          {searchResult.map((result, index) => (
            <div
              key={result + 1}
              className="px-2 py-2 cursor-pointer ml-3"
              onClick={() => {
                ResultClickEvent(result);
              }}
              onKeyDown={() => {
                ResultClickEvent(result);
              }}
              role="presentation"
            >
              {result}
            </div>
          ))}
        </div>

        {/* 인포 메시지 */}
        {shopLocs.map((loc: any, index) => {
          if (loc.benefits || loc.gifts) {
            return (
              <MapMarker
                key={loc + 1}
                position={{ lat: loc.latitude, lng: loc.longitude }}
                onClick={() => handleMarkerClick(loc)}
              />
            );
          }
          return null;
        })}
      </Map>

      {selectedShop && (
        <div
          className={`${
            showDetails
              ? 'w-full h-[400px] absolute inset-x-0 bottom-0 z-0 bg-white rounded-tl-[22px] rounded-tr-[22px] shadow'
              : 'w-full h-[240px] absolute inset-x-0 bottom-0 z-0 bg-white rounded-tl-[22px] rounded-tr-[22px] shadow'
          }`}
        >
          <div className="flex flex-col justify-center mt-7">
            <div className="flex flex-row justify-center">
              <div>
                <img
                  alt="Logo"
                  src="https://logodownload.org/wp-content/uploads/2017/10/Starbucks-logo.png"
                  className="w-[84px]"
                />
              </div>
              {/* <img src={shopLogo[0].image} className="w-[60px]" />  로고 API 사용 여부 확정 후 주석 해제 */}

              <div className="flex-col ml-5">
                <div>
                  <span className="align-bottom text-black text-xl font-bold font-['SUIT']">
                    {selectedShop.shopName}
                  </span>
                  <span className="ml-1 align-bottom text-black text-xl font-bold font-['SUIT']">
                    {selectedShop.shopLocation}
                  </span>
                  <span className="ml-1 align-middle text-Secondary text-base font-light font-['SUIT']">
                    {selectedShop.category}
                  </span>
                </div>

                <div className="flex flex-row mt-1">
                  <div className="flex bg-g6 w-7 rounded-[5px] justify-center items-center text-white text-[4px] font-['SUIT']">
                    <p>혜택</p>
                  </div>

                  <span
                    className="ml-1 text-g6 text-base font-semibold font-['SUIT'] cursor-pointer"
                    onClick={() => {
                      BenefitClick(selectedShop);
                    }}
                    onKeyDown={() => {
                      BenefitClick(selectedShop);
                    }}
                    role="presentation"
                  >
                    피바다 {userId}님을 위한 혜택 보러 가기
                  </span>
                </div>
                <div className="mt-1 text-black text-base text-Primary font-['SUIT']">
                  {selectedShop.address}
                </div>
                <div className="flex flex-row mt-1 text-base text-Primary font-['SUIT']">
                  <span>
                    <Image
                      src="/DistancePin.png"
                      width="20"
                      height="20"
                      alt="거리 핀"
                    />
                  </span>{' '}
                  내 위치에서 {far}m 거리에 있어요
                </div>
              </div>
            </div>

            {showDetails && (
              <div className="mt-2 flex justify-center">
                <CardCarousel key={benefitInfo + 1} data={benefitInfo} />
              </div>
            )}
          </div>
        </div>
      )}
    </>
  );
}
