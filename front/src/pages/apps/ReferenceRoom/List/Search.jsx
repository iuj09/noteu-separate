import Select, { components } from 'react-select';

/* custon indicator */
const IndicatorsContainer = (props) => {
	const { handleClick } = props.selectProps;
	return (
		<div>
			<components.IndicatorsContainer {...props} className="input-group">
				<button className="btn btn-primary input-group-text" onMouseDown={handleClick}>
                    <i className="mdi mdi-magnify search-icon"></i>
				</button>
			</components.IndicatorsContainer>
		</div>
	);
};

const Search = () => {
	return (
		<Select
			components={{ IndicatorsContainer }}
			placeholder={'Search...'}
			isSearchable
			isClearable
			name="searchWord"
		/>
	);
};

export default Search;