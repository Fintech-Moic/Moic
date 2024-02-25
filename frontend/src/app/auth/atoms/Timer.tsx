import useTimer from '@/hooks/useTimer';

const formatTime = (seconds: number): string => {
  const minutes = Math.floor(seconds / 60)
    .toString()
    .padStart(2, '0');
  const remainingSeconds = (seconds % 60).toString().padStart(2, '0');
  return `${minutes}:${remainingSeconds}`;
};
export default function Timer() {
  const remainingTime = useTimer(179, 992);

  return (
    <div className="w-16 h-8 px-2 text-center border-2 border-Tertiary rounded-[10px] text-red-500 my-1">
      <p>{`${formatTime(remainingTime)}`}</p>
    </div>
  );
}
