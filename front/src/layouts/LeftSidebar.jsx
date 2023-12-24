import React, {useEffect, useRef, useState} from 'react';
import {Link, useLocation} from 'react-router-dom';
import SimpleBar from 'simplebar-react';
import AppMenu from './Menu';

// assets
import logo from '@/assets/images/logo.png';
import logoDark from '@/assets/images/logo-dark.png';
import logoSm from '@/assets/images/logo-sm.png';
import logoDarkSm from '@/assets/images/logo-dark-sm.png';
import { getCustomMenuItems} from './utils/menu';
import {extractClaims} from "@/pages/account/Login/extractClaims.js";
import {fetchData} from "@/pages/otherpages/Profile2/fetchData.js";

const UserBox = () => {
	const memberName = extractClaims().memberName;
	const [memberDto, setMemberDto] = useState({});

	useEffect(() => {
		const data = async () => {
			const userId = extractClaims().userId;
			const token = extractClaims().token;

			const userData = await fetchData(userId, token);

			if (userData) {
				setMemberDto(userData.memberDto);
			}
		};
		data();
	}, []);

	const imageUrl = 'http://localhost:8081' + memberDto.profile;


	return (
		<div className="leftbar-user">
			<Link to="/pages/profile2">
				<img
					src={imageUrl}
					alt="user-image"
					height="42"
					width="42"
					className="rounded-circle shadow-sm"
				/>
				<span className="leftbar-user-name mt-2">{memberName}</span>
			</Link>
		</div>
	);
};

function isNumber(subjectId) {
	// parseInt를 사용하여 숫자로 변환하고, NaN 여부를 확인
	return typeof subjectId === 'string' && !isNaN(parseInt(subjectId));
}

const SideBarContent = () => {
	const location = useLocation();
	const url = location.pathname;
	let subjectsIndex = url.indexOf("/subjects/");
	// "/subjects/" 다음에 숫자를 찾기
	let numberStartIndex = subjectsIndex + "/subjects/".length;
	let numberEndIndex = url.indexOf("/", numberStartIndex);

	if (numberEndIndex === -1) {
		// 숫자가 URL의 끝에 있는 경우
		numberEndIndex = url.length;
	}

	// 숫자 추출
	const subjectId = url.substring(numberStartIndex, numberEndIndex);
	console.log("subjectId :" + subjectId);

	const MENU_ITEMS = [
		{
			key: 'navigation',
			label: 'Navigation',
			isTitle: true,
		},
		{
			key: 'subject',
			label: 'Subject',
			isTitle: false,
			icon: 'uil-home-alt',
			url: '/apps/subjects/list'
		},
		{
			key: 'apps',
			label: 'Apps',
			isTitle: true,
		},
		{
			key: 'apps-notice',
			label: 'Notice',
			isTitle: false,
			icon: 'ri-notification-2-line',
			url: `/apps/subjects/${subjectId}/notices/list`,
		},
		{
			key: 'apps-chat',
			label: 'Chat',
			isTitle: false,
			icon: 'uil-comments-alt',
			url: `/apps/subjects/${subjectId}/chat`,
		},
		{
			key: 'apps-questionPost',
			label: 'QuestionPost',
			isTitle: false,
			icon: 'ri-question-line',
			url: `/apps/subjects/${subjectId}/questionPost/list`,
		},
		{
			key: 'apps-referenceRoom',
			label: 'ReferenceRoom',
			isTitle: false,
			icon: 'ri-folders-line',
			url: `/apps/subjects/${subjectId}/referenceRoom/list`,
		},
		{
			key: 'apps-task',
			label: 'Task',
			isTitle: false,
			icon: 'ri-book-open-line',
			url: `/apps/subjects/${subjectId}/tasks/list`,
		},
	];

	if (subjectsIndex !== -1 && isNumber(subjectId)) {
		return (
			<>
				<UserBox />
				<AppMenu menuItems={MENU_ITEMS} />
				<div className="clearfix" />
			</>
		);
	} else {
		return (
			<>
				<UserBox />
				<AppMenu menuItems={getCustomMenuItems()} />
				<div className="clearfix" />
			</>
		);
	}
};

const LeftSidebar = ({ isCondensed, leftbarDark }) => {
	const menuNodeRef = useRef(null);

	/**
	 * Handle the click anywhere in doc
	 */
	const handleOtherClick = (e) => {
		if (menuNodeRef && menuNodeRef.current && menuNodeRef.current.contains(e.target))
			return;
		// else hide the menubar
		if (document.body) {
			document.body.classList.remove('sidebar-enable');
		}
	};

	useEffect(() => {
		document.addEventListener('mousedown', handleOtherClick, false);

		return () => {
			document.removeEventListener('mousedown', handleOtherClick, false);
		};
	}, []);

	return (
		<div className="leftside-menu" ref={menuNodeRef}>
			<Link to="/" className={`logo ${leftbarDark ? 'logo-light' : 'logo-dark'}`}>
				<span className="logo-lg">
					<img src={leftbarDark ? logo : logoDark} alt="logo" height="16" />
				</span>
				<span className="logo-sm">
					<img src={leftbarDark ? logoSm : logoDarkSm} alt="logo" height="16" />
				</span>
			</Link>

			{!isCondensed && (
				<SimpleBar style={{ maxHeight: '100%' }} scrollbarMaxSize={320}>
					<SideBarContent />
				</SimpleBar>
			)}
			{isCondensed && <SideBarContent />}
		</div>
	);
};

export default LeftSidebar;
